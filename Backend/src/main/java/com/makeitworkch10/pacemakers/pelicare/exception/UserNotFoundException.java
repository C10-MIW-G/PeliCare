package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
