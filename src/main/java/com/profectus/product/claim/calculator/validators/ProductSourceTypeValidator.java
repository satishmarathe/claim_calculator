package com.profectus.product.claim.calculator.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductSourceTypeValidator implements ConstraintValidator<ProductSourceType, String> {

    List<String> validProductTypes = Arrays.asList("MERCH","SALES");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	if(null != value && value.trim().length() > 0) {
    		return validProductTypes.contains(value.trim().toUpperCase());
    	}else {
    		/** in case of the value being null dont do anything **/
    		return true;
    	}
        

    }
}
