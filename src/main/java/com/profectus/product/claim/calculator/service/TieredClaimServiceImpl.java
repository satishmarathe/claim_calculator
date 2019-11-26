package com.profectus.product.claim.calculator.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.repository.DiscountTier;
import com.profectus.product.claim.calculator.repository.MerchRepository;
import com.profectus.product.claim.calculator.repository.MerchSales;
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




	@Override
	//public List<? extends MerchSales>  calculateClaim(String code,LocalDate fromDate,LocalDate toDate) {
	public List <TieredClaimDto>  calculateClaim(String code,LocalDate fromDate,LocalDate toDate) {
		System.out.println("<<<< in service calculateClaim  >>>>");
		List<Sales> salesDataList = null;
		List <TieredClaimDto> results = new ArrayList<>();
		/** get the discount tier config data **/
		List<DiscountTier> discountTierList = discountTierService.get();
		
		/** we need to add time aspect to the toDate to ensure we do not miss any records from db 
		 *  this needs to be done since we have datetime as data type in db 
		 */
		LocalDateTime fromDateTime = LocalDateTime.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth(), 00,00,00);
		LocalDateTime toDateTime = LocalDateTime.of(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth(), 23,59, 59);
		/**TODO externalise this hard coding to enum **/
		if(null != code && code.trim().equalsIgnoreCase("MERCH")) {
			/** merch data **/
			//salesDataList = merchRepository.findAll();

			

			/** TODO need to handle scenario if there are no discount tiers populated in db **/

			/** TODO need to handle scenario in case of the user entered search not returning any records to process **/

			/** iterate over data to be processed **/

		}else {
			/** default to Sales data **/
			System.out.println("going to retrieve sales data ");
			//salesDataList = salesRepository.getDetails(fromDate,toDate);
			salesDataList = salesRepository.getDetails(fromDateTime,toDateTime);
			//List<Sales> salesDataList = salesRepository.findAll();
			if(null != salesDataList && salesDataList.size() > 0 ) {
				System.out.println(" in repo DID GET RESULTS ");
				salesDataList.forEach(data -> System.out.println(data.getSaleAmount()));
			}else {
				System.out.println(" in repo did not get any results ");
			}

			/** TODO need to handle scenario if there are no discount tiers populated in db **/

			/** TODO need to handle scenario in case of the user entered search not returning any records to process **/
			
			
			/** iterate over data to be processed **/
			for(int i=0;i<salesDataList.size();i++) {
				/** as we move through each tier we need the prev tier max amount for our discount calculations **/
				BigDecimal saleAmount = salesDataList.get(i).getSaleAmount();
				BigDecimal prevTierMaxAmount = new BigDecimal(0);
				BigDecimal discountAmount =  new BigDecimal(0);
				for(int j=0;j<discountTierList.size();j++) {
					/** the last tier will have a null / empty max amount - which needs to be handled **/
					DiscountTier discountTier = discountTierList.get(j);
					BigDecimal discountPercent = discountTier.getDiscountPercent().divide(new BigDecimal(100));
					BigDecimal discountMaxAmount = discountTier.getMaxAmount();
					if(null == discountMaxAmount ) {
						/** this is the max tier available **/
						discountAmount = discountAmount.add(((saleAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
					}else {
						/** this tier is not the TOP tier **/
						/** if saleAmount <= Tier's Max Amount **/
						int comparison = saleAmount.compareTo(discountMaxAmount);
						if(comparison <= 0) {
							/** saleAmount <= Tier's Max Amount **/
							discountAmount = discountAmount.add(((saleAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
							/** since the saleAmount is less than the tier's max amount - we no longer need to go to the next higher tier **/
							break;
						}else {
							/** saleAmount > Tier's Max Amount **/
							discountAmount = discountAmount.add(((discountMaxAmount.subtract(prevTierMaxAmount).multiply(discountPercent))));
							/** since the saleAmount is greater than this tier's max amount - we need to go to next tier **/
							/** store this tier's max amount so that it can be used for calculations in the next tier **/
							prevTierMaxAmount = discountMaxAmount;
						}
					}
				}//for each discount tier
				/** at this point we have calculated the discount for this Tx store it for displaying **/
				TieredClaimDto tieredClaimDto = new TieredClaimDto();
				tieredClaimDto.setDiscountAmount(discountAmount);
				tieredClaimDto.setProductCode(salesDataList.get(i).getProductCode());
				tieredClaimDto.setSaleAmount(saleAmount);
				tieredClaimDto.setTxDate(salesDataList.get(i).getTxDate());
				
				/** populate it into the list **/
				results.add(tieredClaimDto);
			}//for each record to process
		}

		return results;
	}

}