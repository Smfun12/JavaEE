package kma.topic8.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<String>> handleError(ConstraintViolationException exception){
        Set<String> errors = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(errors);
    }
}
