package com.profectus.product.claim.calculator.repository;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sales extends MerchSales {
	
	@Column(name = "sale_amount")
	private BigDecimal saleAmount;
	
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
}
