package com.profectus.product.claim.calculator.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer; 

public class TieredClaimDto {

	private String productCode;
	
	@JsonSerialize(using = ToStringSerializer.class) 
	private LocalDate txDate;
	
	private BigDecimal amount;
	private BigDecimal discountAmount;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	public TieredClaimDto() {
		
	}
	public TieredClaimDto(String productCode, LocalDate txDate, BigDecimal amount, BigDecimal discountAmount) {
		super();
		this.productCode = productCode;
		this.txDate = txDate;
		this.amount = amount;
		this.discountAmount = discountAmount;
	}
	@Override
	public String toString() {
		return "TieredClaimDto [productCode=" + productCode + ", txDate=" + txDate + ", saleAmount=" + amount
				+ ", discountAmount=" + discountAmount + "]";
	}
}
