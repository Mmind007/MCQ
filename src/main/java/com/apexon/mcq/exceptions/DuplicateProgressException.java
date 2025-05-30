package com.apexon.mcq.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

//@ControllerAdvice
public class DuplicateProgressException extends RuntimeException{
    public DuplicateProgressException(String message) {
        super(message);
    }

}
