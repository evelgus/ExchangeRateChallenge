package com.exchange.rate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ExchangeException.class, UnsupportedOperationException.class})
    protected ResponseEntity<Object> handleConflict(
                RuntimeException ex, WebRequest request) {
        String customMessage;
        HttpStatus status;
        if (ex instanceof ExchangeException ee) {
            customMessage = ee.getMessage() + ". Error code: " +  ee.getCode() + ". Error type: " + ee.getType();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            customMessage = ex.getMessage();
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }
            return new ResponseEntity<>(customMessage, status);
        }
}
