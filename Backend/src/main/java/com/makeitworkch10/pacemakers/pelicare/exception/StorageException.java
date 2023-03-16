package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * File storing exception
 */
public class StorageException extends RuntimeException{
    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
