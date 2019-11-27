package com.profectus.product.claim.calculator.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MerchSalesDto {
	
	
	private String productCode;
	private LocalDate txDate;
	private BigDecimal saleAmount;

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

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public MerchSalesDto(String productCode, LocalDate txDate, BigDecimal saleAmount) {
		super();
		this.productCode = productCode;
		this.txDate = txDate;
		this.saleAmount = saleAmount;
	}

	@Override
	public String toString() {
		return "MerchSalesDto [productCode=" + productCode + ", txDate=" + txDate + ", saleAmount=" + saleAmount + "]";
	}
}
