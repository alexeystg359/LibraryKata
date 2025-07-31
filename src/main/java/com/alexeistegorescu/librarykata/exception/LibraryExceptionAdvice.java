package com.alexeistegorescu.librarykata.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class LibraryExceptionAdvice {

    private static final String ERROR = "error";

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<Map<String, String>> handleLibraryException(final LibraryException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(ERROR, e.getErrorMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(final ConstraintViolationException e) {
        final Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
