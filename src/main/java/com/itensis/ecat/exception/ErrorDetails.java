package com.itensis.ecat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Long timestamp;
    private String message;
    private java.lang.Class<? extends java.lang.Exception> exceptionClass;
    private String details;
}
