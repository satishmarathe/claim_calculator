package com.profectus.product.claim.calculator.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductSourceTypeValidator.class)
@Documented
public @interface ProductSourceType {
	String message() default "Invalid Product Source";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
