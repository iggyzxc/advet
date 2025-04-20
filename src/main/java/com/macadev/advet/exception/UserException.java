package com.macadev.advet.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public static class UserNotFoundException extends UserException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistsException extends UserException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidUserException extends UserException {
        public InvalidUserException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends UserException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
