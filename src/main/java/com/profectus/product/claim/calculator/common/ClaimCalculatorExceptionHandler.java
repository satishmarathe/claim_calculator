package com.profectus.product.claim.calculator.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ClaimCalculatorExceptionHandler extends ResponseEntityExceptionHandler {
	
	
    @ExceptionHandler(InvalidFormatException.class )
    public void invalidFormatException(HttpServletResponse response) throws IOException {
    	System.out.println(" ");
    	System.out.println("<<< inside advice invalidFormatException >>>>");
    	System.out.println(" ");
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    
    @ExceptionHandler(ConstraintViolationException.class )
    public void constraintViolationException(HttpServletResponse response) throws IOException {
    	System.out.println(" ");
    	System.out.println("<<< inside advice constraintViolationException >>>>");
    	System.out.println(" ");
    	response.sendError(HttpStatus.BAD_REQUEST.value());
    }

	
}