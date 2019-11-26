package com.profectus.product.claim.calculator.common;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductSourceTypeValidator implements ConstraintValidator<ProductSourceType, String> {

    List<String> validProductTypes = Arrays.asList("MERCH","SALES");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	
        return validProductTypes.contains(value.trim().toUpperCase());

    }
}
