package io.takima.master3.store.customer;

import io.takima.master3.store.core.exceptions.ApiException;
import io.takima.master3.store.core.presentation.ExceptionEntity;
import io.takima.master3.store.discount.DiscountException;
import io.takima.master3.store.discount.models.Offer;
import org.springframework.http.HttpStatus;

public class MyCustomException extends ApiException {
    public enum Code {
        MIN_PRICE("discount.min_price", HttpStatus.NOT_ACCEPTABLE, "The cart does not have the required minimal price for this offer to apply"),
        MAX_PRICE("discount.max_price", HttpStatus.NOT_ACCEPTABLE, "The cart does exceeds the maximal price for this offer to apply"),
        MIN_QUANTITY("discount.min_quantity", HttpStatus.NOT_ACCEPTABLE, "The cart does not have the required minimal article quantity for this offer to apply"),
        MAX_QUANTITY("discount.max_quantity", HttpStatus.NOT_ACCEPTABLE, "The cart exceeds the maximal article quantity for this offer to apply"),
        START_DATE("discount.start_date", HttpStatus.NOT_FOUND, "The offer is not started yet"),
        EXPIRE_DATE("discount.expire_date", HttpStatus.GONE, "The offer has expired"),
        APPLICABLE_ARTICLES("discount.applicable_articles", HttpStatus.NOT_ACCEPTABLE, "The offer does not applies to the given article selection");
        private final String code;
        private final HttpStatus status;
        private final String message;

        Code(String code, HttpStatus status, String message) {
            this.code = code;
            this.status = status;
            this.message = message;
        }
        public String toString() {
            return this.code;
        }
    }

    public MyCustomException(Code code, Offer offer) {
        super( new ExceptionEntity.Builder()
                .message(code.message)
                .status(code.status)
                .code(code.code)
                .data(offer)
                .build());
    }
    public MyCustomException(DiscountException.Code code, Offer offer, String message) {
        super( new ExceptionEntity.Builder()
                .message(code.message)
                .status(code.status)
                .data(offer)
                .build());
    }

}
