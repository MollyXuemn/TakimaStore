package io.takima.master3.store.core.presentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @JsonIgnore
    private Throwable cause;



    /**
     *
     * @param message The exception's message, typically got from {@link Exception#getMessage()}
     * @param status the Http status this exception is mapped to
     * @param code any custom API error code
     * @param headers any custom header to be set when this exception is raised
     * @param data any additional data this exception refers to.
     */
    public ExceptionEntity(@NotNull Throwable cause,
                           @Nullable String message,
                           @Nullable HttpStatus status,
                           @Nullable String code,
                           @Nullable HttpHeaders headers,
                           @Nullable Object data) {
        this.cause = cause;
        this.message = message != null ? message : cause.getMessage();
        this.status = status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = code;
        this.headers = headers != null ? headers : new HttpHeaders();
        this.data = data;

    }

    public ExceptionEntity(ExceptionEntity exceptionEntity) {
        ExceptionEntity ee = exceptionEntity;
        this.status = ee.status;
        this.cause = ee.cause;
        this.message = ee.message;
        this.code = ee.code;
        this.headers = ee.headers;
        this.data = ee.data;
        this.timestamp = ee.timestamp;

    }

    public ExceptionEntity() {

    }
    public Throwable getCause() {
        return this.cause;
    }


    public ResponseEntity<ExceptionEntity> toResponseEntity(WebRequest request) {
        return new ResponseEntity<>(this, headers, status);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Nullable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(@Nullable HttpStatus status) {
        this.status = status;
    }

    @Nullable
    public String getCode() {
        return this.code;
    }

    public void setCode(@Nullable String code) {
        this.code = code;
    }

    @Nullable
    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(@Nullable HttpHeaders headers) {
        this.headers = headers;
    }

    @Nullable
    public Object getData() {
        return data;
    }
    public void setData(@Nullable Object data) {
        this.data = data;
    }

    public static class Builder {
        private ExceptionEntity exceptionEntity = new ExceptionEntity();
        public ExceptionEntity.Builder cause(Throwable cause) {
            this.exceptionEntity.cause = cause;
            return this;
        }

        public ExceptionEntity.Builder message(String message) {
            this.exceptionEntity.message = message;
            return this;
        }

        public ExceptionEntity.Builder status(HttpStatus status) {
            this.exceptionEntity.status = status;
            return this;
        }

        public ExceptionEntity.Builder code(String code) {
            this.exceptionEntity.code = code;
            return this;
        }

        public ExceptionEntity.Builder headers(HttpHeaders headers) {
            this.exceptionEntity.headers = headers;
            return this;
        }

        public ExceptionEntity.Builder data(Object data) {
            this.exceptionEntity.data = data;
            return this;
        }
        public ExceptionEntity build() {
            return new ExceptionEntity(exceptionEntity);
        }


    }

}
