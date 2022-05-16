package com.company.springboot.technicaltest.advice;

import com.company.springboot.technicaltest.exception.ConstraintException;
import com.company.springboot.technicaltest.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintException.class)
    protected ResponseEntity handleConstraintViolation(ConstraintException ex) {
        return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity(ex.getFieldError().getDefaultMessage() ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity handleNotFoundResource(ResourceNotFoundException ex) {
        return new ResponseEntity("Resource not found" ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity handleNoSuchElement(NoSuchElementException ex) {
        return new ResponseEntity("Element not found" ,HttpStatus.NOT_FOUND);
    }
}
