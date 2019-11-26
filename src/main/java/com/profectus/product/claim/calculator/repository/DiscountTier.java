package com.profectus.product.claim.calculator.repository;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discount_tiers")
public class DiscountTier {
    @Id
    @Column(name = "tier_id")
    private Integer tierId;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;
    
    @Column(name = "discount_rate")
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
    
    

}