package com.epam.esm.rest;

import com.epam.esm.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.ResourceBundle;

@ControllerAdvice
public class RestExceptionHandler {

    ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.forLanguageTag("ru"));

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(),
                messages.getString("errorMessage"),
                String.valueOf(HttpStatus.BAD_REQUEST.value()));
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
