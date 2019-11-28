package com.profectus.product.claim.calculator.service;

import java.util.List;

import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.exception.BusinessException;
import com.profectus.product.claim.calculator.exception.SystemException;

public interface TieredClaimService {
	/** TODO should we have parameters as same POJO as that used in persistence **/
	/** TODO datatype for date needs thought **/
	 //List<? extends MerchSales>   calculateClaim(String code,LocalDate fromDate,LocalDate toDate);
	 List <TieredClaimDto>   calculateClaim(ClaimRequestDto claimRequestDto,String xAppCorelationId) throws SystemException;
	
}
