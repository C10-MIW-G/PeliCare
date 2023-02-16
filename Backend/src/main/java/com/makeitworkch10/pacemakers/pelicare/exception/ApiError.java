package com.makeitworkch10.pacemakers.pelicare.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Ruben de Vries
 * Model for handling exceptions.
 */

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private String message;
    private Integer statusCode;
    private String statusText;
    private LocalDateTime timeStamp;
}
