package io.takima.master3.store.discount.services.impl;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.cart.models.Cart;
import io.takima.master3.store.cart.persistence.CartDao;
import io.takima.master3.store.discount.DiscountException;
import io.takima.master3.store.discount.models.DiscountedCart;
import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.discount.persistence.OfferDao;
import io.takima.master3.store.discount.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.takima.master3.store.discount.DiscountException.Code.APPLICABLE_ARTICLES;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final OfferDao offerDao;
    private final CartDao cartDao;
    private final Clock clock;

    @Autowired
    public DiscountServiceImpl(OfferDao offerDao, CartDao cartDao, Clock clock) {
        this.offerDao = offerDao;
        this.cartDao = cartDao;
        this.clock = clock;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public Cart applyOffers(Cart cart) {
        LocalDateTime now = LocalDateTime.now(clock);

        // convert into a DiscountedCart to compute the new discounted total
        DiscountedCart discountedCart = new DiscountedCart(cart);

        // cart may already comes with offers; Remove outdated offers
        pruneOutdatedOffers(discountedCart, now);

        // find and apply all no code offers
        return findAndApplyOffers(discountedCart, now);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public Cart addOffer(Cart cart, String code) {
        if (code == null || "".equals(code)) {
            throw new NoSuchElementException("No code provided");
        }

        LocalDateTime now = LocalDateTime.now(clock);

        DiscountedCart discountedCart = new DiscountedCart(cart);

        Offer offer = offerDao.findByCode(code).orElseThrow(() -> new NoSuchElementException(String.format("No offer found with the code '%s'", code)));

        OfferContext offerContext = new OfferContext(discountedCart, offer, now);

        Set<Article> articles = offerContext.getApplicableArticles();
        if (articles.isEmpty()){
            throw new DiscountException(APPLICABLE_ARTICLES, offer);
        }

        pruneOutdatedOffers(discountedCart, now);

        offerContext.assertIsValid();

        discountedCart.applyOffer(offer);

        return findAndApplyOffers(discountedCart, now);
    }

    @Override
    @Transactional
    public Cart removeOffer(Cart cart, String code) throws DiscountException {
        if (code == null || "".equals(code)) {
            throw new NoSuchElementException("No code provided");
        }

        LocalDateTime now = LocalDateTime.now(clock);

        Set<Offer> allOffersApplied = cart.getOffers();

        Offer offer = offerDao.findByCode(code).orElseThrow(() -> new NoSuchElementException(String.format("No offer found with the code '%s'", code)));

        if (!allOffersApplied.contains(offer)){
            throw new NoSuchElementException("Code not applied to this cart");
        }

        allOffersApplied.remove(offer);

        DiscountedCart discountedCart = new DiscountedCart(cart);

        allOffersApplied.stream()
                .sorted()   // apply offers by priority order
                .map(o -> new OfferContext(discountedCart, o, now))
                // keep only offers that fulfills quantity conditions
                .filter(c -> c.isQuantityCriteriaMet(false))
                .forEach(c -> {
                    // apply only offers that fulfills price conditions
                    if (c.isPriceCriteriaMet(false)) {
                        c.cart.applyOffer(c.offer);
                    }
                });

        return findAndApplyOffers(discountedCart, now);
    }

    /* ***
     * Private
     */

    private Cart findAndApplyOffers(DiscountedCart cart, LocalDateTime date) {
        pruneOutdatedOffers(cart, date);

        List<Offer> candidateOffers = new LinkedList<>();

        // if cart has no articles: nothing to do.
        if (cart.getArticles().isEmpty()) {
            return cart;
        }

        // add all offers that automatically applies to cart (without code)
        candidateOffers.addAll(offerDao.findOffersWithoutCode(date));

        // add all offers that automatically applies to article selection (without code)
        candidateOffers.addAll(offerDao.findOffersWithoutCode(date, cart.getArticles().keySet()));

        // apply all offers in the right order
        candidateOffers.stream()
                .sorted()   // apply offers by priority order
                .map(o -> new OfferContext(cart, o, date))
                // keep only offers that fulfills quantity conditions
                .filter(c -> c.isQuantityCriteriaMet(false))
                .forEach(c -> {
                    // apply only offers that fulfills price conditions
                    if (c.isPriceCriteriaMet(false)) {
                        c.cart.applyOffer(c.offer);
                    }
                });

        // update cart
        cartDao.update(new Cart(cart));

        return cart;
    }

    /**
     * remove all offers that are no longer between validity dates.
     */
    private void pruneOutdatedOffers(DiscountedCart cart, LocalDateTime date) {

        // get already applied codes
        Set<Offer> oldOffers = cart.getOffers();

        // clear discountedTotal (total = originalTotal) + remove all offers
        cart.resetOffers();

        oldOffers.stream()
                // keep only offers with code
                .filter(Offer::hasCode)
                .map(o -> new OfferContext(cart, o, date))
                // keep only offers still valid for this date
                // keep only offers that fulfills quantity conditions
                .filter(OfferContext::isValid)
                // apply all offers by priority order
                .sorted(Comparator.comparing(c2 -> c2.offer))
                .forEach(c -> {
                    // keep only offers that fulfills price conditions
                    if (c.isPriceCriteriaMet(false)) {
                        cart.applyOffer(c.offer);
                    }
                });
    }

    private static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
        // optimization to iterate over the smaller set
        if (setA.size() > setB.size()) {
            return intersection(setB, setA);
        }

        return setA.stream().filter(setB::contains).collect(Collectors.toSet());
    }

    class OfferContext {
        private final LocalDateTime date;
        private final DiscountedCart cart;
        private final Offer offer;
        private Set<Article> applicableArticles;

        public OfferContext(DiscountedCart cart, Offer offer, LocalDateTime date) {
            this.cart = cart;
            this.date = date;
            this.offer = offer;
        }

        private void assertIsValid() {
            isDateCriteriaMet(true);
            isQuantityCriteriaMet(true);
            isPriceCriteriaMet(true);
        }

        private boolean isValid() {
            return isDateCriteriaMet(false) &&
                    isQuantityCriteriaMet(false) &&
                    isPriceCriteriaMet(false);
        }

        public boolean isQuantityCriteriaMet(boolean shouldThrow) {
            // if offer does not specify a minimum and maximum quantity => return true;
            if (!offer.hasQuantityCriteria()) {
                return true;
            }

            int articlesQuantity = cart.getArticles().entrySet()
                    .stream()
                    .filter(e -> getApplicableArticles().contains(e.getKey()))
                    .mapToInt(Map.Entry::getValue)
                    .sum();

            if (offer.getMinQuantity() != null && offer.getMinQuantity() > articlesQuantity) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.MIN_QUANTITY, offer,
                            String.format("expected at least %d articles", offer.getMinQuantity()));
                }

                return false;
            }

            if (offer.getMaxQuantity() != null && offer.getMaxQuantity() < articlesQuantity) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.MAX_QUANTITY, offer,
                            String.format("expected at most %d articles", offer.getMinQuantity()));
                }

                return false;
            }

            return true;
        }

        public boolean isPriceCriteriaMet(boolean shouldThrow) {
            if (!offer.hasPriceCriteria()) {
                return true;
            }

            var total = cart.getTotal();

            if (offer.getMaxPrice() != null && offer.getMaxPrice().compareTo(total) < 0) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.MAX_PRICE, offer,
                            String.format("expected minimum cart total of %s", offer.getMinPrice()));
                }
                return false;
            }
            if (offer.getMinPrice() != null && offer.getMinPrice().compareTo(total) > 0) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.MIN_PRICE, offer,
                            String.format("expected minimum cart total of %s", offer.getMinPrice()));
                }
                return false;
            }

            return true;
        }

        public boolean isDateCriteriaMet(boolean shouldThrow) {
            // check that date is between offer' s validity dates
            if (offer.getStartDate().isAfter(date)) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.START_DATE, offer, "Offer does not exist");
                }
                return false;

            } else if (offer.getExpireDate().isBefore(date)) {
                if (shouldThrow) {
                    throw new DiscountException(DiscountException.Code.EXPIRE_DATE, offer, "Offer has expired");
                }

                return false;
            }

            return true;
        }

        private Set<Article> getApplicableArticles() {

            if (applicableArticles != null) {
                return applicableArticles;
            }

            // if offer does not apply to an article selection => apply to the whole cart.
            if (offer.getArticles().isEmpty()) {
                return cart.getArticles().keySet();
            }

            // return the articles of the cart that belongs to the offer's applicable articles.
            applicableArticles = intersection(cart.getArticles().keySet(), offer.getArticles());

            return applicableArticles;
        }
    }
}

