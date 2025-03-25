package com.lugui14.simplified_transactions.infrastructure.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> error400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> useCaseError(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record ValidationErrorData(String field, String message) {
        public ValidationErrorData(FieldError fielderror) {
            this(fielderror.getField(), fielderror.getDefaultMessage());
        }
    }
}
