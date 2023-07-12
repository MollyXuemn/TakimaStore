package io.takima.master3.store.core.exceptions;

import io.takima.master3.store.core.presentation.ExceptionEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionEntity> handleInternalError(Exception e, WebRequest request) {
        e.printStackTrace();
        return ExceptionEntity.builder()
                .cause(e)
                .status(resolveAnnotatedResponseStatus(e))
                .build()
                .toResponseEntity(request);
    }

    @ExceptionHandler({NoSuchElementException.class, NoSuchFieldException.class})
    public ResponseEntity<ExceptionEntity> handleNotFound(Exception e, WebRequest request) {
        e.printStackTrace();
        return Objects.requireNonNull(new ResponseEntity<>(ExceptionEntity.builder()
                        .cause(e)
                        .status(HttpStatus.NOT_FOUND)
                        .build(), HttpStatus.NOT_FOUND)
                        .getBody())
                .toResponseEntity(request);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionEntity> handleNullPointerException(NullPointerException e, WebRequest request) {
        e.printStackTrace();
        return Objects.requireNonNull(new ResponseEntity<>(ExceptionEntity.builder()
                        .message(e.getMessage())
                        .cause(e)
                        .status(HttpStatus.NO_CONTENT)
                        .build(), HttpStatus.NO_CONTENT)
                        .getBody())
                .toResponseEntity(request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionEntity> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        e.printStackTrace();
        return Objects.requireNonNull(new ResponseEntity<>(ExceptionEntity.builder()
                        .message(e.getMessage())
                        .cause(e)
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .build(), HttpStatus.NOT_ACCEPTABLE)
                        .getBody())
                .toResponseEntity(request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionEntity> handleDataIntegrityViolationException(Throwable e, WebRequest request) {
        e.printStackTrace();
        return Objects.requireNonNull(new ResponseEntity<>(ExceptionEntity.builder()
                        .cause(e)
                        .code("db.constraint-violation")
                        .status(HttpStatus.BAD_REQUEST)
                        .build(), HttpStatus.BAD_REQUEST)
                        .getBody())
                .toResponseEntity(request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionEntity> handleMissingServletRequestParameterException(Throwable e, WebRequest request) {
        e.printStackTrace();
        return Objects.requireNonNull(new ResponseEntity<>(ExceptionEntity.builder()
                        .cause(e)
                        .code("db.constraint-violation")
                        .status(HttpStatus.BAD_REQUEST)
                        .build(), HttpStatus.BAD_REQUEST)
                        .getBody())
                .toResponseEntity(request);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Throwable t) {
        ResponseStatus status = findMergedAnnotation(t.getClass(), ResponseStatus.class);
        return status != null ? status.value() : null;
    }


}
