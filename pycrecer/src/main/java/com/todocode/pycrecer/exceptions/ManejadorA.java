package com.todocode.pycrecer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorA {
    @ExceptionHandler(AsistenteNoEncontradoException.class)
    public ResponseEntity<String> AsistenteNoEncontradoException(AsistenteNoEncontradoException ane) {
        return new ResponseEntity<>(ane.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejaExceptionGeneral(Exception ex){
        return new ResponseEntity<> ("error interno "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
