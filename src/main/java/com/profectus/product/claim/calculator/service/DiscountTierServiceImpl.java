package com.profectus.product.claim.calculator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profectus.product.claim.calculator.common.Constants;
import com.profectus.product.claim.calculator.repository.DiscountTier;
import com.profectus.product.claim.calculator.repository.DiscountTierDto;
import com.profectus.product.claim.calculator.repository.DiscountTierRepository;

@Service
public class DiscountTierServiceImpl implements DiscountTierService {
	@Autowired
	private DiscountTierRepository discountTierRepository;

	@Override
	public List<DiscountTierDto> get() {
		/** we need to only read it once since this data does not change we can store it in a common collection **/
		if(null == Constants.discountTierList || Constants.discountTierList.size() <= 0 ) {
			/** we need to retrieve the data from db and populate this data into common collection **/
			List<DiscountTier> discountTierList = discountTierRepository.findAllByOrderByTierIdAsc();
			if(null != discountTierList && discountTierList.size() > 0) {
				/** transform entity pojo to a pure DTO **/
				discountTierList.forEach(data -> {
					Constants.discountTierList.add(new DiscountTierDto(data.getTierId(), data.getMinAmount(), data.getMaxAmount(), data.getDiscountPercent()));
				});
				
			}			
		}		
		return Constants.discountTierList;
	}
}
