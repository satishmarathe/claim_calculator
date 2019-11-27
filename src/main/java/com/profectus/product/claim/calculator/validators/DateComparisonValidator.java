package com.profectus.product.claim.calculator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.profectus.product.claim.calculator.dto.ClaimRequestDto;

public class DateComparisonValidator implements ConstraintValidator<DateComparison, ClaimRequestDto> {

    

    @Override
    public boolean isValid(ClaimRequestDto dto, ConstraintValidatorContext context) {
    	/** the toDate should be greater than or equal to the from date 
    	 *  i.e. the fromDate should never be greater than toDate
    	 */
    	if(null != dto.getFromDate() && null != dto.getToDate()) {
    		return dto.getToDate().isEqual(dto.getFromDate()) || dto.getToDate().isAfter(dto.getFromDate());
    	}else {
    		/** do not perform this validation if any one or both dates are null **/
    		return true;
    	}
        

    }
}
