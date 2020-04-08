package com.itensis.ecat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getMessage(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class) //TODO maybe delete?
    public final ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), ex.getMessage(), ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<? extends ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        List<String> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(new Date().getTime(), ex.getMessage(), validationErrors, ex.getClass(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
