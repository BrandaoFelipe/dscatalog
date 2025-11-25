package com.brandao.dscatalog.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brandao.dscatalog.dtos.errorHandlers.CustomError;
import com.brandao.dscatalog.dtos.errorHandlers.ValidationError;
import com.brandao.dscatalog.services.exceptions.DatabaseException;
import com.brandao.dscatalog.services.exceptions.EmailException;
import com.brandao.dscatalog.services.exceptions.EmptyRequestException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomError> objNotFound(NotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), "Not Found Exception", e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmptyRequestException.class)
    public ResponseEntity<CustomError> emptyRequest(EmptyRequestException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), "Empty Request Exception", e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), "Database Exception", e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> invalidArgument(MethodArgumentNotValidException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        e.getBindingResult().getFieldErrors()
                .forEach(f -> err.addError(f.getField(), f.getDefaultMessage()));

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<CustomError> email(EmailException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), "Email Exception", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }



}
