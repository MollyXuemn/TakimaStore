package io.takima.master3.store.core.presentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class ExceptionEntity {
    private LocalDateTime timestamp = LocalDateTime.now();
    @Nullable
    private String message;
    @Nullable
    @JsonIgnore
    private HttpStatus status;
    @Nullable
    private String code;
    @Nullable
    @JsonIgnore
    private HttpHeaders headers;
    @Nullable
    private Object data;

    /**
     *
     * @param message The exception's message, typically got from {@link Exception#getMessage()}
     * @param status the Http status this exception is mapped to
     * @param code any custom API error code
     * @param headers any custom header to be set when this exception is raised
     * @param data any additional data this exception refers to.
     */
    public ExceptionEntity(@Nullable String message,
                           @Nullable HttpStatus status,
                           @Nullable String code,
                           @Nullable HttpHeaders headers,
                           @Nullable Object data) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.headers = headers;
        this.data = data;
    }

    public ResponseEntity<ExceptionEntity> toResponseEntity(WebRequest request) {
        return new ResponseEntity<>(this, headers, status);
    }
}
