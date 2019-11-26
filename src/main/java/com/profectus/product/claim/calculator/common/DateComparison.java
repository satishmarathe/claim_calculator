package com.profectus.product.claim.calculator.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateComparisonValidator.class)
@Documented
public @interface DateComparison {
	String message() default "From Date should not be greater than To Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
