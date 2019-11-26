package com.profectus.product.claim.calculator.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@IdClass(MerchSalesPK.class)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class MerchSales {
	
	@Id
	@Column(name = "product_code")
	private String productCode;
	
	@Id
	@Column(name = "tx_date")
	private LocalDate txDate;
	
	@Column(name = "sale_amount")
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
}
