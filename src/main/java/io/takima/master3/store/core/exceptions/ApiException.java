package io.takima.master3.store.core.exceptions;

import io.takima.master3.store.core.presentation.ExceptionEntity;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final ExceptionEntity body;

    public ApiException(@Nullable String message,
                        @Nullable Throwable cause,
                        @Nullable HttpStatus status,
                        @Nullable String code,
                        @Nullable HttpHeaders headers,
                        @Nullable Object data) {
        this(cause, ExceptionEntity
                .builder()
                .message(message)
                .status(status)
                .code(code)
                .headers(headers)
                .data(data)
                .build()
        );
    }

    public ApiException(Throwable cause, ExceptionEntity body) {
        super(body.getMessage(), cause);
        this.body = body;
    }

    public ApiException(ExceptionEntity body) {
        super(body.getMessage());
        this.body = body;
    }

    public ExceptionEntity getExceptionEntity() {
        return body;
    }
}

