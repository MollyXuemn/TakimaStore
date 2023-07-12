package io.takima.master3.store.core.presentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@Getter
@Setter
public class ExceptionEntity {
    private LocalDateTime timestamp = LocalDateTime.now();
    @NotNull
    @JsonIgnore
    private HttpStatus status;
    @Nullable
    private String message;
    @Nullable
    private String code;
    @NotNull
    @JsonIgnore
    private HttpHeaders headers;
    @Nullable
    private Object data;
    @JsonIgnore
    private Throwable cause;

    /**
     * @param status  the Http status this exception is mapped to
     * @param message The exception's message, typically got from {@link Exception#getMessage()}
     * @param code    any custom API error code
     * @param headers any custom header to be set when this exception is raised
     * @param data    any additional data this exception refers to.
     */
    @Builder
    private ExceptionEntity(@NotNull Throwable cause,
                            @Nullable HttpStatus status,
                            @Nullable String message,
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
        this.status = exceptionEntity.status;
        this.cause = exceptionEntity.cause;
        this.message = exceptionEntity.message;
        this.code = exceptionEntity.code;
        this.headers = exceptionEntity.headers;
        this.data = exceptionEntity.data;
        this.timestamp = exceptionEntity.timestamp;
    }

    public ResponseEntity<ExceptionEntity> toResponseEntity(WebRequest request) {
        return new ResponseEntity<>(this, headers, status);
    }

    public Throwable getCause() {
        return this.cause;
    }
}

