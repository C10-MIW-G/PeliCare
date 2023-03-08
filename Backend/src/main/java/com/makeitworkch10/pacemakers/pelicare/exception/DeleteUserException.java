package com.makeitworkch10.pacemakers.pelicare.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ruben de Vries
 * Throws an exception when a user cannot be deleted due to constraints.
 */

@Getter
@Setter
public class DeleteUserException extends RuntimeException {

    private List<String> careCircleNames;
    public DeleteUserException(String message, List<String> careCircleNames){
        super(message);
        this.careCircleNames = careCircleNames;
    }

}
