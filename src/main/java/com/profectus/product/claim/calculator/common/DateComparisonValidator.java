package com.profectus.product.claim.calculator.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.profectus.product.claim.calculator.dto.ClaimRequestDto;

public class DateComparisonValidator implements ConstraintValidator<DateComparison, ClaimRequestDto> {

    

    @Override
    public boolean isValid(ClaimRequestDto dto, ConstraintValidatorContext context) {
    	/** the toDate should be greater than or equal to the from date 
    	 *  i.e. the fromDate should never be greater than toDate
    	 */
        return dto.getToDate().isEqual(dto.getFromDate()) || dto.getToDate().isAfter(dto.getFromDate());

    }
}
