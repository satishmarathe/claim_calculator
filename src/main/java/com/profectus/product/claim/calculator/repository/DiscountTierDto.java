package com.profectus.product.claim.calculator.repository;

import java.math.BigDecimal;


public class DiscountTierDto {
    private Integer tierId;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal discountPercent;

	public Integer getTierId() {
		return tierId;
	}

	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public DiscountTierDto(Integer tierId, BigDecimal minAmount, BigDecimal maxAmount, BigDecimal discountPercent) {
		super();
		this.tierId = tierId;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.discountPercent = discountPercent;
	}

	@Override
	public String toString() {
		return "DiscountTier [tierId=" + tierId + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount
				+ ", discountPercent=" + discountPercent + "]";
	}
    
    

}