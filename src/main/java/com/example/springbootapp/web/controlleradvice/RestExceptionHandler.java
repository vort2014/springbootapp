package com.example.springbootapp.web.controlleradvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    // if jakarta @RequestBody fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(errorMessages);
    }

    // if jakarta @RequestParam/@PathVariable fails
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<List<String>> handleConstraintViolation(ConstraintViolationException ex) {
        var errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ResponseEntity.badRequest().body(errorMessages);
    }
}
