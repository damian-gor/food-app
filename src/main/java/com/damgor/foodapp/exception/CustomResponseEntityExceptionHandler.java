package com.damgor.foodapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError apiError = new ApiError(FORBIDDEN);
        String requestURL = request.getDescription(true).substring(request.getDescription(true).indexOf('/'), request.getDescription(true).indexOf(';'));
        apiError.setMessage(String.format("This http method on URL %s is available only for profile's owner and admin", requestURL));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}