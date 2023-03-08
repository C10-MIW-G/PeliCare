package com.makeitworkch10.pacemakers.pelicare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Ruben de Vries
 * This class handles custom exceptions and overrides standard exceptions.
 * The exceptions handled by this class wil be sent to the front-end as an ApiError object.
 */

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return buildResponseEntity(new ApiError(exception.getMessage(), status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException() {
        String badCredentialsMessage = "Invalid username or password";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return  buildResponseEntity(new ApiError(badCredentialsMessage, status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(InvalidReCaptchaException.class)
    protected ResponseEntity<Object> handleInvalidReCaptchaException(
            InvalidReCaptchaException invalidReCaptchaException
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return  buildResponseEntity(new ApiError(invalidReCaptchaException.getMessage(), status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(DuplicateUserException.class)
    protected ResponseEntity<Object> handleDuplicateUserException(
            DuplicateUserException duplicateUserException
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        return  buildResponseEntity(new ApiError(duplicateUserException.getMessage(), status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException userNotFoundException
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return  buildResponseEntity(new ApiError(userNotFoundException.getMessage(), status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(WrongPasswordException.class)
    protected ResponseEntity<Object> handleWrongPasswordException(
            WrongPasswordException wrongPasswordException
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return  buildResponseEntity(new ApiError(wrongPasswordException.getMessage(), status.value(),
                status.toString(), LocalDateTime.now()), status);
    }

    @ExceptionHandler(DeleteUserException.class)
    protected ResponseEntity<Object> handleDeleteUserException(
            DeleteUserException deleteUserException
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        return  buildResponseEntity(new ApiError(deleteUserException.getMessage(), status.value(),
                status.toString(), LocalDateTime.now(), deleteUserException.getCareCircleNames()), status);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

}
