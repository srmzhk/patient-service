package com.srmzhk.patientservice.exception;

import com.srmzhk.patientservice.dto.ResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExTranslator {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> noContent(ItemNotFoundException ex) {
        return ResponseEntity.status(NO_CONTENT).body(null);
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    public ResponseEntity<ResponseError> notValidName(InvalidNameFormatException ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ResponseError(new Timestamp(System.currentTimeMillis()),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        ex.getMessage(),
                        ServletUriComponentsBuilder.fromCurrentRequest().toUriString()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> accessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(FORBIDDEN)
                .body(new ResponseError(new Timestamp(System.currentTimeMillis()),
                        FORBIDDEN.value(),
                        FORBIDDEN.getReasonPhrase(),
                        ex.getMessage(),
                        ServletUriComponentsBuilder.fromCurrentRequest().toUriString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> internalServerError(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ResponseError(new Timestamp(System.currentTimeMillis()),
                        INTERNAL_SERVER_ERROR.value(),
                        INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        ex.getMessage(),
                        ServletUriComponentsBuilder.fromCurrentRequest().toUriString()));
    }
}

