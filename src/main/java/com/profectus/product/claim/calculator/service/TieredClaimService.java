package com.profectus.product.claim.calculator.service;

import java.time.LocalDate;
import java.util.List;

import com.profectus.product.claim.calculator.dto.TieredClaimDto;

public interface TieredClaimService {
	/** TODO should we have parameters as same POJO as that used in persistence **/
	/** TODO datatype for date needs thought **/
	 //List<? extends MerchSales>   calculateClaim(String code,LocalDate fromDate,LocalDate toDate);
	 List <TieredClaimDto>   calculateClaim(String code,LocalDate fromDate,LocalDate toDate);
	
}
