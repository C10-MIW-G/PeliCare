package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @author Ruben de Vries
 * Controls ReCaptcha exceptions.
 */

public class InvalidReCaptchaException extends RuntimeException{
    public InvalidReCaptchaException(String message) {
        super(message);
    }
}
