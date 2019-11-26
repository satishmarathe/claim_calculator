package com.profectus.product.claim.calculator.common;

import java.util.ArrayList;
import java.util.List;

import com.profectus.product.claim.calculator.repository.DiscountTier;

public final class Constants {
	private Constants() {
	}
	
	/** will store the one time read of Discount Tier rates  **/
	public static final List<DiscountTier> discountTierList = new ArrayList<DiscountTier>();
	
	
}
