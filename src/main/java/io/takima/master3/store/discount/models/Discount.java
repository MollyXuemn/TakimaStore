package io.takima.master3.store.discount.models;

import io.takima.master3.store.core.models.Price;

interface Discount {
    Price getOriginalPrice();

    void applyOffer(Offer offer);
}
