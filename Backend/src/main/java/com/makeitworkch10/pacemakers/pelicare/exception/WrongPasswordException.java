package com.makeitworkch10.pacemakers.pelicare.exception;

/**
 * @Author: Ramon de Wilde <r.de.wilde@st.hanze.nl>
 * <p>
 * "FUNCTIE VAN PROJECT"
 */
public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String message) {
        super(message);
    }

}
