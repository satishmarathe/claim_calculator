package com.profectus.product.claim.calculator.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {
    /**
     * Exception handler for validation errors caused by method    parameters @RequesParam, @PathVariable, @RequestHeader annotated with javax.validation constraints.
    */
    @ExceptionHandler
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception)    {

        List<ApiError> apiErrors = new ArrayList<>();

        for (ConstraintViolation<?> violation :  exception.getConstraintViolations()) {
            String value = (violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString());
            apiErrors.add(new  ApiError(violation.getPropertyPath().toString(), value, violation.getMessage()));
}

return ResponseEntity.badRequest().body(apiErrors);
    }
} 
