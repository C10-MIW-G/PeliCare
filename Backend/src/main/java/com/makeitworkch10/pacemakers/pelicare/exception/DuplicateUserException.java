package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super(message);
    }
}
