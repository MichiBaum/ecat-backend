package com.itensis.ecat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class) //TODO maybe delete?
    public final ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getMessage(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getMessage(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
