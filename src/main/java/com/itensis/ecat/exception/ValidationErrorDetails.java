package com.itensis.ecat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDetails extends ErrorDetails {
    private Long timestamp;
    private String message;
    private List<String> validationErrors;
    private String details;
}
