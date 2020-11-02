package com.epam.esm.rest;

import com.epam.esm.Translator;
import com.epam.esm.rest.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    Translator translator;

    @Autowired
    public RestExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage() + " " + translator.toLocale("notFound"),
                String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(),
                translator.toLocale("errorMessage"),
                String.valueOf(HttpStatus.BAD_REQUEST.value()));
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
