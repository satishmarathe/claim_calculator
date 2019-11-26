package com.profectus.product.claim.calculator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profectus.product.claim.calculator.common.Constants;
import com.profectus.product.claim.calculator.repository.DiscountTier;
import com.profectus.product.claim.calculator.repository.DiscountTierRepository;
import com.profectus.product.claim.calculator.repository.User;
import com.profectus.product.claim.calculator.repository.UserRepository;

@Service
public class DiscountTierServiceImpl implements DiscountTierService {
	@Autowired
	private DiscountTierRepository discountTierRepository;

	@Override
	public List<DiscountTier> get() {
		/** we need to only read it once since this data does not change we can store it in a common collection **/
		if(null == Constants.discountTierList || Constants.discountTierList.size() <= 0 ) {
			/** we need to retrieve the data from db and populate this data into common collection **/
			Constants.discountTierList.addAll(discountTierRepository.findAllByOrderByTierIdAsc());
		}		
		return Constants.discountTierList;
	}
}
