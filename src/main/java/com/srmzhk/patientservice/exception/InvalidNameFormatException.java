package com.srmzhk.patientservice.exception;

public class InvalidNameFormatException extends RuntimeException {
    public InvalidNameFormatException(String message) {
        super(message);
    }
}
