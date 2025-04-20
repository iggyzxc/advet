package com.macadev.advet.exception;

import com.macadev.advet.util.FeedbackMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles specific user not found exceptions
    @ExceptionHandler(UserException.UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(
            UserException.UserNotFoundException exception,
            WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.USER_NOT_FOUND,
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    // Handles specific user already exists exceptions
    @ExceptionHandler(UserException.UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(
            UserException.UserAlreadyExistsException exception,
            WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.USER_ALREADY_EXISTS,
                webRequest.getDescription(false),
                "USER_ALREADY_EXISTS"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserException.EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleEmailAlreadyExistsException(
            UserException.EmailAlreadyExistsException exception,
            WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.EMAIL_ALREADY_EXISTS,
                webRequest.getDescription(false),
                "EMAIL_ALREADY_EXISTS"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    public ResponseEntity<ErrorMessage> handleInvalidUserException(
            UserException.InvalidUserException exception,
            WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.INVALID_USER_TYPE,
                webRequest.getDescription(false),
                "INVALID_USER_TYPE"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // Handles specific resource not found exceptions
    @ExceptionHandler(UserException.ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(
            UserException.ResourceNotFoundException exception,
            WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.RESOURCE_NOT_FOUND,
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    // Handles all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                FeedbackMessage.INTERNAL_SERVER_ERROR,
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
