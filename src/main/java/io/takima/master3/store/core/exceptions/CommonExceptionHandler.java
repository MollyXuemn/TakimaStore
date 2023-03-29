package io.takima.master3.store.core.exceptions;

import io.takima.master3.store.core.presentation.ExceptionEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionEntity> handleNullPointerException(NullPointerException e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .cause(e.getCause())
                .headers(HttpHeaders.EMPTY)
                .code("No valable request Exception")
                .message(e.getMessage())
                .status(resolveAnnotatedResponseStatus(e))
                .build().toResponseEntity(request);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ExceptionEntity> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .cause(e.getCause())
                .headers(HttpHeaders.writableHttpHeaders(new HttpHeaders()))
                .code("No such element ! ")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build().toResponseEntity(request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionEntity> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        e.printStackTrace();

        return new ExceptionEntity.Builder()
                .cause(e.getCause())
                .headers(HttpHeaders.EMPTY)
                .code("Illegal Argument Exception")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build().toResponseEntity(request);
    }
    private HttpStatus resolveAnnotatedResponseStatus(Throwable t) {
        ResponseStatus status = findMergedAnnotation(t.getClass(), ResponseStatus.class);
        return status != null ? status.value() : null;
    }


}
