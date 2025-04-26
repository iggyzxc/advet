package com.macadev.advet.exception;

import lombok.Getter;

// For general input errors
@Getter
public class InvalidInputException extends AppException {
    public InvalidInputException(String message) {
        super(message);
    }
}
