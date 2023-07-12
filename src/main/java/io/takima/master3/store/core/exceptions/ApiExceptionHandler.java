package io.takima.master3.store.core.exceptions;

import io.takima.master3.store.core.presentation.ExceptionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ExceptionEntity> handleApiException(ApiException e, WebRequest request) {
        e.printStackTrace();
        return e.getExceptionEntity().toResponseEntity(request);
    }
}

