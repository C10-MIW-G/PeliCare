package com.makeitworkch10.pacemakers.pelicare.exception;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Ruben de Vries
 * Model for handling exceptions.
 */

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiError {
    @NonNull
    private String message;
    @NonNull
    private Integer statusCode;
    @NonNull
    private String statusText;
    @NonNull
    private LocalDateTime timeStamp;
    private Object errorObject;
}
