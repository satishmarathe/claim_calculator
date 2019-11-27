package com.profectus.product.claim.calculator.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.profectus.product.claim.calculator.validators.DateComparison;
import com.profectus.product.claim.calculator.validators.ProductSourceType; 

@Validated
@DateComparison
public class ClaimRequestDto {

	@NotEmpty(message = "The product source cannot be null or empty")
	@ProductSourceType
	String productTypeSource;	

	@JsonSerialize(using = ToStringSerializer.class) 
	@NotNull(message = "FromDate cannot be null or empty")
	LocalDate fromDate;

	@JsonSerialize(using = ToStringSerializer.class) 
	@NotNull(message = "ToDate cannot be null or empty")
	LocalDate toDate;

	public ClaimRequestDto() {
	}

	public ClaimRequestDto(String productTypeSource, LocalDate fromDate, LocalDate toDate) {
		super();
		System.out.println("DTO constructor values source = " + productTypeSource + " fromDate = " + fromDate + " toDate = " + toDate);
		this.productTypeSource = productTypeSource;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}


	public String getProductTypeSource() {
		System.out.println("DTO get source = " + productTypeSource);
		return productTypeSource;
	}
	public void setProductTypeSource(String productTypeSource) {
		System.out.println("DTO set source = " + productTypeSource);
		this.productTypeSource = productTypeSource;
	}
	public LocalDate getFromDate() {
		System.out.println("DTO get fromDate = " + fromDate);
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		System.out.println("DTO set fromDate = " + fromDate);
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		System.out.println("DTO get toDate = " + toDate);
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		System.out.println("DTO set toDate = " + toDate);
		this.toDate = toDate;
	}


	@Override
	public String toString() {
		return "ClaimRequestDto [productTypeSource=" + productTypeSource + ", fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}
}
