package com.chn.exam.common.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleEntityNotFound(EntityNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Entity Not Found");
        pd.setProperty("error_category", "Business Rule");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(DuplicateEntityException.class)
    ProblemDetail handleDuplicateNIT(DuplicateEntityException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        pd.setTitle("Duplicate Entity");
        pd.setProperty("error_category", "Business Rule");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(BusinessException.class)
    ProblemDetail handleBusinessException(BusinessException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Business Exception");
        pd.setProperty("error_category", "Business Rule");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation(ConstraintViolationException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Constraint Violation");
        pd.setProperty("error_category", "Validation");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });

        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Validation Error");
        pd.setDetail("Se ha producido uno o más errores de validación en la solicitud.");
        pd.setProperty("error_category", "Validation");
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("errors", errors);

        return new ResponseEntity<>(pd, headers, status);
    }
}
