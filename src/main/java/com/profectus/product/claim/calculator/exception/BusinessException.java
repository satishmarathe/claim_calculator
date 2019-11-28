package com.profectus.product.claim.calculator.exception;

public class BusinessException extends RuntimeException { 
	public BusinessException(String errorMessage) {
		super(errorMessage);
	}
}