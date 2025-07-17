package com.apexon.mcq.exceptions;

import jakarta.servlet.http.HttpServletRequest;  // or javax.servlet.http.HttpServletRequest depending on your setup
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SkillGlobalExceptionHandler {

    @ExceptionHandler(SkillResourceNotFoundException.class)
    public ResponseEntity<SkillApiErrorResponse> handleResourceNotFound(SkillResourceNotFoundException ex, HttpServletRequest request) {
    	SkillApiErrorResponse response = new SkillApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
//                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SkillBadRequestException.class)
    public ResponseEntity<SkillApiErrorResponse> handleBadRequest(SkillBadRequestException ex, HttpServletRequest request) {
    	SkillApiErrorResponse response = new SkillApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
//                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SkillApiErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
    	SkillApiErrorResponse response = new SkillApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred"
//                request.getRequestURI()
        );
        ex.printStackTrace(); // Optional: log stacktrace

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

