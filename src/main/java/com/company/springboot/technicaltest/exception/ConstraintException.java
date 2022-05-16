package com.company.springboot.technicaltest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ConstraintException extends Exception{
    public ConstraintException(String message){
        super(message);
    }
}
