package io.takima.master3.store.discount.models;

import io.takima.master3.store.core.models.Price;

public interface Discount {
    Price getOriginalPrice();

    void applyOffer(Offer offer);
}
