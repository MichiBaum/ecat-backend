package com.itensis.ecat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDetails extends ErrorDetails {
    private Long timestamp;
    private String message;
    private List<String> validationErrors;
    private java.lang.Class<? extends java.lang.Exception> exceptionClass;
    private String details;
}
