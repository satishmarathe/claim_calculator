package com.profectus.product.claim.calculator.repository;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "merch")
public class Merch extends MerchSales {
	
	@Column(name = "purchase_amount")
	private BigDecimal purchaseAmount;
	
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
}
