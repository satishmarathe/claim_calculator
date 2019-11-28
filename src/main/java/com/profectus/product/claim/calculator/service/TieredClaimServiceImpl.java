package com.profectus.product.claim.calculator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.dto.MerchSalesDto;
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.exception.BusinessException;
import com.profectus.product.claim.calculator.exception.SystemException;
import com.profectus.product.claim.calculator.repository.DiscountTierDto;
import com.profectus.product.claim.calculator.repository.Merch;
import com.profectus.product.claim.calculator.repository.MerchRepository;
import com.profectus.product.claim.calculator.repository.Sales;
import com.profectus.product.claim.calculator.repository.SalesRepository;

@Service
public class TieredClaimServiceImpl implements TieredClaimService {

	@Autowired
	private MerchRepository merchRepository;

	@Autowired
	private SalesRepository salesRepository;

	@Autowired
	private DiscountTierService discountTierService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TieredClaimServiceImpl.class);


	@Override
	//public List<? extends MerchSales>  calculateClaim(String code,LocalDate fromDate,LocalDate toDate) {
	public List <TieredClaimDto>  calculateClaim(ClaimRequestDto claimRequestDto,String xAppCorelationId) throws SystemException {
		final String METHOD_NAME = "calculateClaim" ;
		
		LOGGER.info(" {} | {} | Entering input data is  ...  {} ",METHOD_NAME,"xAppCorelationId",claimRequestDto);
		
		List<MerchSalesDto> merchsalesDtoList = new ArrayList<>();
		List <TieredClaimDto> results = new ArrayList<>();
		/** get the discount tier config data **/
		List<DiscountTierDto> discountTierList = discountTierService.get();
		
		if(null == discountTierList || discountTierList.size() <=0) {
			SystemException systemException = new SystemException("No Discount Tier data Found");
			/** we need atleast one tiered discount data without which we need to raise an error **/
			LOGGER.error(" {} | {} | {} | {} | No discount tier configuration data found ! ",METHOD_NAME,xAppCorelationId,systemException.getMessage(),systemException);
			throw systemException;
		}
		
		LOGGER.info(" {} | {} | DiscountTierData is  ...  {} ",METHOD_NAME,"xAppCorelationId",discountTierList);
		/** we need to add time aspect to the toDate to ensure we do not miss any records from db 
		 *  this needs to be done since we have datetime as data type in db 
		 */
		LocalDate fromDate = claimRequestDto.getFromDate();
		LocalDate toDate = claimRequestDto.getToDate();
		LocalDateTime fromDateTime = LocalDateTime.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth(), 00,00,00);
		LocalDateTime toDateTime = LocalDateTime.of(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth(), 23,59, 59);		
		
		/**TODO externalise this hard coding to enum **/
		if(null != claimRequestDto.getProductTypeSource() && claimRequestDto.getProductTypeSource().trim().equalsIgnoreCase("MERCH")) {
			/** merch data **/
			LOGGER.info(" {} | {} | retrieve data for MERCH with following dates  ...  {} and ... {} ",METHOD_NAME,"xAppCorelationId",claimRequestDto,fromDateTime,toDateTime);
			List<Merch> merchDataList = merchRepository.getDetails(fromDateTime,toDateTime);
			if(null != merchDataList && merchDataList.size() > 0) {
				/** transform entity pojo to a pure DTO **/
				merchDataList.forEach(data -> {
					merchsalesDtoList.add(new MerchSalesDto(data.getProductCode(),data.getTxDate(),data.getPurchaseAmount()));					
				});
			}
			/** TODO need to handle scenario in case of the user entered search not returning any records to process **/
		}else {
			/** default to Sales data **/
			LOGGER.info(" {} | {} | retrieve data for SALES with following dates  ...  {} and ... {} ",METHOD_NAME,"xAppCorelationId",claimRequestDto,fromDateTime,toDateTime);
			//salesDataList = salesRepository.getDetails(fromDate,toDate);
			List<Sales> salesDataList = salesRepository.getDetails(fromDateTime,toDateTime);
			if(null != salesDataList && salesDataList.size() > 0) {
				/** transform entity pojo to a pure DTO **/
				salesDataList.forEach(data -> {
					merchsalesDtoList.add(new MerchSalesDto(data.getProductCode(),data.getTxDate(),data.getSaleAmount()));					
				});
			}
		}
		
		/** before we proceed further check if the user entered criteria produced any Tx Records
		 *  if no records then go back and let user know 
		 *  Though this is NOT an exception - we would need to let end user know 
		 */
		if(null == merchsalesDtoList || merchsalesDtoList.size() <= 0) {
			/** user entered search provided no records - send back **/
			BusinessException businessException = new BusinessException("No data found for user defined criteria - Please change input data  !");
			/** we need atleast one tiered discount data without which we need to raise an error **/
			LOGGER.error(" {} | {} | {} | {} | No data found for user defined criteria - Please change input data  ! ",METHOD_NAME,xAppCorelationId,businessException.getMessage(),businessException);
			throw businessException;
		}
		
		LOGGER.info(" {} | {} | all validations passed so we will begin calculations  ...   ",METHOD_NAME,"xAppCorelationId");
		/** iterate over data to be processed **/
		for(int i=0;i<merchsalesDtoList.size();i++) {
			/** as we move through each tier we need the prev tier max amount for our discount calculations **/
			BigDecimal saleAmount = merchsalesDtoList.get(i).getSaleAmount();
			BigDecimal prevTierMaxAmount = new BigDecimal(0);
			BigDecimal discountAmount =  new BigDecimal(0);
			for(int j=0;j<discountTierList.size();j++) {
				/** the last tier will have a null / empty max amount - which needs to be handled **/
				DiscountTierDto discountTierDto = discountTierList.get(j);
				BigDecimal discountPercent = discountTierDto.getDiscountPercent().divide(new BigDecimal(100));
				BigDecimal discountMaxAmount = discountTierDto.getMaxAmount();
				if(null == discountMaxAmount ) {
					/** this is the max tier available **/
					discountAmount = discountAmount.add(((saleAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
					LOGGER.info(" {} | {} | Calculating in top tier saleAmount is {} and discount percent is {} and discountAmount is  ...   {}",METHOD_NAME,"xAppCorelationId",saleAmount,discountPercent,discountAmount);
				}else {
					/** this tier is not the TOP tier **/
					/** if saleAmount <= Tier's Max Amount **/
					int comparison = saleAmount.compareTo(discountMaxAmount);
					if(comparison <= 0) {
						/** saleAmount <= Tier's Max Amount **/
						discountAmount = discountAmount.add(((saleAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
						/** since the saleAmount is less than the tier's max amount - we no longer need to go to the next higher tier **/
						LOGGER.info(" {} | {} | Calculating in tier saleAmount within tier, saleAmount is {} and discount percent is {} and discountAmount is  ...   {}",METHOD_NAME,"xAppCorelationId",saleAmount,discountPercent,discountAmount);
						break;
					}else {
						/** saleAmount > Tier's Max Amount **/
						discountAmount = discountAmount.add(((discountMaxAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
						/** since the saleAmount is greater than this tier's max amount - we need to go to next tier **/
						/** store this tier's max amount so that it can be used for calculations in the next tier **/
						prevTierMaxAmount = discountMaxAmount;
						LOGGER.info(" {} | {} | Calculating in tier saleAmount beyond tier, saleAmount is {} and discount percent is {} and discountAmount is  ...   {}",METHOD_NAME,"xAppCorelationId",saleAmount,discountPercent,discountAmount);
					}
				}
			}//for each discount tier
			/** at this point we have calculated the discount for this Tx store it for displaying **/
			TieredClaimDto tieredClaimDto = new TieredClaimDto();
			tieredClaimDto.setDiscountAmount(discountAmount);
			tieredClaimDto.setProductCode(merchsalesDtoList.get(i).getProductCode());
			tieredClaimDto.setAmount(saleAmount);
			tieredClaimDto.setTxDate(merchsalesDtoList.get(i).getTxDate());
			
			LOGGER.info(" {} | {} | Calculation completed for a record results are   ... {}  ",METHOD_NAME,"xAppCorelationId",tieredClaimDto);
			
			/** populate it into the list **/
			results.add(tieredClaimDto);
		}//for each record to process
		LOGGER.info(" {} | {} | Exiting successfully ",METHOD_NAME,"xAppCorelationId");
		return results;
	}

}
