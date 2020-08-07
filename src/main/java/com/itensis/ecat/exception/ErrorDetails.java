package com.itensis.ecat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Long timestamp;
    private String message;
    private String details;
}
