package com.macadev.advet.exception;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime timestamp,
                           String message,
                           String errorDescription,
                           String errorCode) {
}
