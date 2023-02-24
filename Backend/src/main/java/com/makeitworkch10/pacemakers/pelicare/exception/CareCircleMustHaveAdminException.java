package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * A Care Circle should always have at least one Circle Admin.
 * Trying to revoke the admin status of the last Admin in the Care Circle,
 * will throw this exception and inform the User.
 */
public class CareCircleMustHaveAdminException extends RuntimeException{
    public CareCircleMustHaveAdminException(String message) {
        super(message);
    }
}
