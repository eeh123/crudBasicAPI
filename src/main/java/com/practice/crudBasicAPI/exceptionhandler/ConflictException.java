package com.practice.crudBasicAPI.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT) //409 Conflict status code
public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
