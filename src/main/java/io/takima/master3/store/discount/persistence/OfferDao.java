package io.takima.master3.store.discount.persistence;

import io.takima.master3.store.discount.models.Offer;
import io.takima.master3.store.article.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface OfferDao extends CrudRepository<Offer, Long> {
    /**
     * Find an Offer by its code. A discount code is unique.
     * @param code the code of the discount to search for.
     * @return the discount that
     */
    Optional<Offer> findByCode(String code);

    /**
     * Find Cart offers that has no discount code.
     * A Cart Offer is an Offer that references no articles, and applies to all articles.
     * @param period period of validity
     * @return all offers with no codes and no articles between the two dates
     */
    Set<Offer> findOffersWithoutCode(LocalDateTime period);

    /**
     * Find Cart offers that has no discount code.
     * A Cart Offer is an Offer that references no articles, and applies to all articles.
     * @param startDate date the offer starts
     * @param expireDate date the offer expires
     * @return all offers with no codes and no articles between the two dates
     */
    Set<Offer> findOffersWithoutCode(LocalDateTime startDate, LocalDateTime expireDate);

    /**
     * Find Article offers that has no discount code.
     * An Article Offer is an Offer that references some articles to be applied to
     *
     * @param period period of validity
     * @param articles the articles offers should apply to
     * @return all offers with no codes and the given articles between the two dates
     */
    Set<Offer> findOffersWithoutCode(LocalDateTime period, Set<? extends Article> articles);

    /**
     * Find Article offers that has no discount code.
     * An Article Offer is an Offer that references some articles to be applied to
     *
     * @param startDate date the offer starts
     * @param expireDate date the offer expires
     * @param articles the articles offers should apply to
     * @return all offers with no codes and the given articles between the two dates
     */
    Set<Offer> findOffersWithoutCode(LocalDateTime startDate, LocalDateTime expireDate, Set<? extends Article> articles);
}
