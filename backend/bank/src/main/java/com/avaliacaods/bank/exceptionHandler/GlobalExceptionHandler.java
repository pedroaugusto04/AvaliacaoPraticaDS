package com.avaliacaods.bank.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.avaliacaods.bank.exceptions.InvalidCpfException;
import com.avaliacaods.bank.exceptions.InvalidTransactionException;
import com.avaliacaods.bank.models.exception.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTransaction(InvalidTransactionException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                ex.getSubMessage() != null ? ex.getSubMessage() : "");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCpf(InvalidCpfException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                ex.getSubMessage() != null ? ex.getSubMessage() : "");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse("Não foi possível realizar login","Credenciais incorretas");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }
}