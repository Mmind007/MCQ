package com.apexon.mcq.exceptions;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
