package com.github.gather.exception;

import com.sun.istack.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCustomException(@NotNull IllegalArgumentException ie){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ie.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException be) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(be.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleDisabledException(DisabledException de) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(de.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleLockedException(LockedException le) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(le.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException dve) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dve.getMessage());
    }

}
