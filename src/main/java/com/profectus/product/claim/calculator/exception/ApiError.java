package com.profectus.product.claim.calculator.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;




public class ApiError {

	@JsonIgnore
	private int code;
	
	private String field;
	private String value;
	private String message;

	public ApiError(String message) {
		this.message = message;
	}

	public ApiError(String field, String value, String message) {
		this.field = field;
		this.value = value;
		this.message = message;
	}
}