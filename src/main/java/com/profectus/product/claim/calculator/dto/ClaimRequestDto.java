package com.profectus.product.claim.calculator.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.profectus.product.claim.calculator.common.ProductSourceType;
import com.profectus.product.claim.calculator.common.DateComparison;

@DateComparison(message = "test message")
public class ClaimRequestDto {
	
	@NotEmpty(message = "The product source cannot be null or empty")
	@ProductSourceType	
	String productTypeSource;	
	
	@NotNull(message = "FromDate cannot be null or empty")	
	LocalDate fromDate;

	@NotNull(message = "ToDate cannot be null or empty")	
	LocalDate toDate;
	
	public ClaimRequestDto() {
	}
	
	public ClaimRequestDto(String productTypeSource, LocalDate fromDate, LocalDate toDate) {
		super();
		this.productTypeSource = productTypeSource;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}


	public String getProductTypeSource() {
		return productTypeSource;
	}
	public void setProductTypeSource(String productTypeSource) {
		this.productTypeSource = productTypeSource;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}


	@Override
	public String toString() {
		return "ClaimRequestDto [productTypeSource=" + productTypeSource + ", fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}
}
