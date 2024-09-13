package com.myfirstspringboot.spring_crud.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<NotFoundException> handleNotFoundException(NotFoundException ex) {
        log.error("Resource not found: " + ex.getMessage());
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }
}
