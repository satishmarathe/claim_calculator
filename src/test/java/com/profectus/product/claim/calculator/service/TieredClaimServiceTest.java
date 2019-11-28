package com.profectus.product.claim.calculator.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profectus.product.claim.calculator.common.Constants;
import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.exception.BusinessException;
import com.profectus.product.claim.calculator.exception.SystemException;
import com.profectus.product.claim.calculator.repository.DiscountTierDto;
import com.profectus.product.claim.calculator.repository.Merch;
import com.profectus.product.claim.calculator.repository.MerchRepository;
import com.profectus.product.claim.calculator.repository.Sales;
import com.profectus.product.claim.calculator.repository.SalesRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TieredClaimServiceTest {

	@Autowired
	private TieredClaimService tieredClaimService;	

	@MockBean
	private SalesRepository salesRepository;

	@MockBean
	private MerchRepository merchRepository;



	//@Autowired
	//private DiscountTierService discountTierService;


	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("Positive Business scenario : Discount should be correct for Tx amount within Tier 1 ")
	void tieredClaimPositiveScenarioWithinTier(String sourceType) throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		//DiscountTierService discountTierService = mock(DiscountTierService.class);
		//DiscountTierService discountTierService = spy(new DiscountTierServiceImpl());
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking the discount tier data that would be retrieved from master table **/		
		List<DiscountTierDto> mockDiscountTierList = new ArrayList<>();
		DiscountTierDto mockDiscountTierDto1 = new DiscountTierDto(1,BigDecimal.valueOf(0),BigDecimal.valueOf(1000),BigDecimal.valueOf(2));
		DiscountTierDto mockDiscountTierDto2 = new DiscountTierDto(2,BigDecimal.valueOf(1001),BigDecimal.valueOf(5000),BigDecimal.valueOf(5));
		DiscountTierDto mockDiscountTierDto3 = new DiscountTierDto(3,BigDecimal.valueOf(5001),null,BigDecimal.valueOf(7.5));

		mockDiscountTierList.add(mockDiscountTierDto1);
		mockDiscountTierList.add(mockDiscountTierDto2);
		mockDiscountTierList.add(mockDiscountTierDto3);

		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		Constants.discountTierList.add(mockDiscountTierDto1);
		Constants.discountTierList.add(mockDiscountTierDto2);
		Constants.discountTierList.add(mockDiscountTierDto3);
		//doReturn(mockDiscountTierList).when(discountTierService).get();

		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * List<Sales> salesDataList  = salesRepository.getDetails(fromDateTime,toDateTime);
		 * 
		 * **/

		List<Sales> mockedSalesDtoList  = new ArrayList<>();
		List<Merch> mockedMerchDtoList  = new ArrayList<>();

		/**
		 * THE AMOUNT THAT WE SET HERE is CRITICAL - this amount along with tier cut off amounts and discount will decide the output values.
		 * so we should have multiple tests for multiple scenarios with amounts to make sure all scenarios and border cases are considered.
		 */
		Sales mockedSalesDto = new Sales();
		mockedSalesDto.setProductCode("A");
		mockedSalesDto.setSaleAmount(BigDecimal.valueOf(400));
		mockedSalesDto.setTxDate(LocalDate.of(2019,07,01));

		Merch mockedMerchDto = new Merch();
		mockedMerchDto.setProductCode("A");
		mockedMerchDto.setPurchaseAmount(BigDecimal.valueOf(400));
		mockedMerchDto.setTxDate(LocalDate.of(2019,07,01));

		mockedSalesDtoList.add(mockedSalesDto);
		mockedMerchDtoList.add(mockedMerchDto);

		/** now return this mocked List **/
		if(sourceType.trim().equalsIgnoreCase("merch")) {
			doReturn(mockedMerchDtoList).when(merchRepository).getDetails(any(),any());
		}else {
			doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());
		}






		/** Execute the service call **/
		List <TieredClaimDto> tieredClaimDtoList = tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId");



		//Assertions.assertNotNull(tieredClaimDtoList);
		Assertions.assertEquals(0, BigDecimal.valueOf(8.00).compareTo(tieredClaimDtoList.get(0).getDiscountAmount()));



	}

	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("Positive Business scenario : Discount should be correct for Tx amount at limit of Tier 1 ")
	void tieredClaimPositiveScenarioBoundaryCaseTier1(String sourceType) throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		//DiscountTierService discountTierService = mock(DiscountTierService.class);
		//DiscountTierService discountTierService = spy(new DiscountTierServiceImpl());
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking the discount tier data that would be retrieved from master table **/		
		List<DiscountTierDto> mockDiscountTierList = new ArrayList<>();
		DiscountTierDto mockDiscountTierDto1 = new DiscountTierDto(1,BigDecimal.valueOf(0),BigDecimal.valueOf(1000),BigDecimal.valueOf(2));
		DiscountTierDto mockDiscountTierDto2 = new DiscountTierDto(2,BigDecimal.valueOf(1001),BigDecimal.valueOf(5000),BigDecimal.valueOf(5));
		DiscountTierDto mockDiscountTierDto3 = new DiscountTierDto(3,BigDecimal.valueOf(5001),null,BigDecimal.valueOf(7.5));

		mockDiscountTierList.add(mockDiscountTierDto1);
		mockDiscountTierList.add(mockDiscountTierDto2);
		mockDiscountTierList.add(mockDiscountTierDto3);

		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		Constants.discountTierList.add(mockDiscountTierDto1);
		Constants.discountTierList.add(mockDiscountTierDto2);
		Constants.discountTierList.add(mockDiscountTierDto3);
		//doReturn(mockDiscountTierList).when(discountTierService).get();

		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * List<Sales> salesDataList  = salesRepository.getDetails(fromDateTime,toDateTime);
		 * 
		 * **/

		List<Sales> mockedSalesDtoList  = new ArrayList<>();
		List<Merch> mockedMerchDtoList  = new ArrayList<>();

		/**
		 * THE AMOUNT THAT WE SET HERE is CRITICAL - this amount along with tier cut off amounts and discount will decide the output values.
		 * so we should have multiple tests for multiple scenarios with amounts to make sure all scenarios and border cases are considered.
		 */
		Sales mockedSalesDto = new Sales();
		mockedSalesDto.setProductCode("A");
		mockedSalesDto.setSaleAmount(BigDecimal.valueOf(1000));
		mockedSalesDto.setTxDate(LocalDate.of(2019,07,01));

		Merch mockedMerchDto = new Merch();
		mockedMerchDto.setProductCode("A");
		mockedMerchDto.setPurchaseAmount(BigDecimal.valueOf(1000));
		mockedMerchDto.setTxDate(LocalDate.of(2019,07,01));

		mockedSalesDtoList.add(mockedSalesDto);
		mockedMerchDtoList.add(mockedMerchDto);

		/** now return this mocked List **/
		doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());

		doReturn(mockedMerchDtoList).when(merchRepository).getDetails(any(),any());


		/** Execute the service call **/
		List <TieredClaimDto> tieredClaimDtoList = tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId");



		//Assertions.assertNotNull(tieredClaimDtoList);
		Assertions.assertEquals(0, BigDecimal.valueOf(20.00).compareTo(tieredClaimDtoList.get(0).getDiscountAmount()));



	}



	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("Positive Business scenario : Discount should be correct for Tx amount within Tier 2 ")
	void tieredClaimPositiveScenarioWithinTier2(String sourceType) throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		//DiscountTierService discountTierService = mock(DiscountTierService.class);
		//DiscountTierService discountTierService = spy(new DiscountTierServiceImpl());
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking the discount tier data that would be retrieved from master table **/		
		List<DiscountTierDto> mockDiscountTierList = new ArrayList<>();
		DiscountTierDto mockDiscountTierDto1 = new DiscountTierDto(1,BigDecimal.valueOf(0),BigDecimal.valueOf(1000),BigDecimal.valueOf(2));
		DiscountTierDto mockDiscountTierDto2 = new DiscountTierDto(2,BigDecimal.valueOf(1001),BigDecimal.valueOf(5000),BigDecimal.valueOf(5));
		DiscountTierDto mockDiscountTierDto3 = new DiscountTierDto(3,BigDecimal.valueOf(5001),null,BigDecimal.valueOf(7.5));

		mockDiscountTierList.add(mockDiscountTierDto1);
		mockDiscountTierList.add(mockDiscountTierDto2);
		mockDiscountTierList.add(mockDiscountTierDto3);

		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		Constants.discountTierList.add(mockDiscountTierDto1);
		Constants.discountTierList.add(mockDiscountTierDto2);
		Constants.discountTierList.add(mockDiscountTierDto3);
		//doReturn(mockDiscountTierList).when(discountTierService).get();

		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * List<Sales> salesDataList  = salesRepository.getDetails(fromDateTime,toDateTime);
		 * 
		 * **/

		List<Sales> mockedSalesDtoList  = new ArrayList<>();
		List<Merch> mockedMerchDtoList  = new ArrayList<>();

		/**
		 * THE AMOUNT THAT WE SET HERE is CRITICAL - this amount along with tier cut off amounts and discount will decide the output values.
		 * so we should have multiple tests for multiple scenarios with amounts to make sure all scenarios and border cases are considered.
		 */
		Sales mockedSalesDto = new Sales();
		mockedSalesDto.setProductCode("A");
		mockedSalesDto.setSaleAmount(BigDecimal.valueOf(4600));
		mockedSalesDto.setTxDate(LocalDate.of(2019,07,01));

		mockedSalesDtoList.add(mockedSalesDto);

		Merch mockedMerchDto = new Merch();
		mockedMerchDto.setProductCode("A");
		mockedMerchDto.setPurchaseAmount(BigDecimal.valueOf(4600));
		mockedMerchDto.setTxDate(LocalDate.of(2019,07,01));

		mockedMerchDtoList.add(mockedMerchDto);

		/** now return this mocked List **/
		doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());

		doReturn(mockedMerchDtoList).when(merchRepository).getDetails(any(),any());


		/** Execute the service call **/
		List <TieredClaimDto> tieredClaimDtoList = tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId");



		//Assertions.assertNotNull(tieredClaimDtoList);
		Assertions.assertEquals(0, BigDecimal.valueOf(200.00).compareTo(tieredClaimDtoList.get(0).getDiscountAmount()));



	}

	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("Positive Business scenario : Discount should be correct for Tx amount in Top Tier  ")
	void tieredClaimPositiveScenarioTopTier(String sourceType) throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		//DiscountTierService discountTierService = mock(DiscountTierService.class);
		//DiscountTierService discountTierService = spy(new DiscountTierServiceImpl());
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking the discount tier data that would be retrieved from master table **/		
		List<DiscountTierDto> mockDiscountTierList = new ArrayList<>();
		DiscountTierDto mockDiscountTierDto1 = new DiscountTierDto(1,BigDecimal.valueOf(0),BigDecimal.valueOf(1000),BigDecimal.valueOf(2));
		DiscountTierDto mockDiscountTierDto2 = new DiscountTierDto(2,BigDecimal.valueOf(1001),BigDecimal.valueOf(5000),BigDecimal.valueOf(5));
		DiscountTierDto mockDiscountTierDto3 = new DiscountTierDto(3,BigDecimal.valueOf(5001),null,BigDecimal.valueOf(7.5));

		mockDiscountTierList.add(mockDiscountTierDto1);
		mockDiscountTierList.add(mockDiscountTierDto2);
		mockDiscountTierList.add(mockDiscountTierDto3);

		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		Constants.discountTierList.add(mockDiscountTierDto1);
		Constants.discountTierList.add(mockDiscountTierDto2);
		Constants.discountTierList.add(mockDiscountTierDto3);
		//doReturn(mockDiscountTierList).when(discountTierService).get();

		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * List<Sales> salesDataList  = salesRepository.getDetails(fromDateTime,toDateTime);
		 * 
		 * **/

		List<Sales> mockedSalesDtoList  = new ArrayList<>();
		List<Merch> mockedMerchDtoList  = new ArrayList<>();

		/**
		 * THE AMOUNT THAT WE SET HERE is CRITICAL - this amount along with tier cut off amounts and discount will decide the output values.
		 * so we should have multiple tests for multiple scenarios with amounts to make sure all scenarios and border cases are considered.
		 */


		Sales mockedSalesDto = new Sales();
		mockedSalesDto.setProductCode("A");
		mockedSalesDto.setSaleAmount(BigDecimal.valueOf(7200));
		mockedSalesDto.setTxDate(LocalDate.of(2019,07,01));

		Merch mockedMerchDto = new Merch();
		mockedMerchDto.setProductCode("A");
		mockedMerchDto.setPurchaseAmount(BigDecimal.valueOf(7200));
		mockedMerchDto.setTxDate(LocalDate.of(2019,07,01));

		mockedSalesDtoList.add(mockedSalesDto);
		mockedMerchDtoList.add(mockedMerchDto);



		/** now return this mocked List **/
		doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());

		doReturn(mockedMerchDtoList).when(merchRepository).getDetails(any(),any());


		/** Execute the service call **/
		List <TieredClaimDto> tieredClaimDtoList = tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId");

		

		//Assertions.assertNotNull(tieredClaimDtoList);
		Assertions.assertEquals(0, BigDecimal.valueOf(385.00).compareTo(tieredClaimDtoList.get(0).getDiscountAmount()));



	}
	
	@Test
	@DisplayName("Negative System scenario : Should throw exception if Discount Tier data is not populated  ")
	void tieredClaimNegativeScenarioDiscountTierDataMissing() throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("merch" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking scenario where there is NO discount tier data which is bad and service method should throw an exception **/		
		
		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		
		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * List<Sales> salesDataList  = salesRepository.getDetails(fromDateTime,toDateTime);
		 * 
		 * **/

		List<Sales> mockedSalesDtoList  = new ArrayList<>();
		
		/**
		 * THE AMOUNT THAT WE SET HERE is CRITICAL - this amount along with tier cut off amounts and discount will decide the output values.
		 * so we should have multiple tests for multiple scenarios with amounts to make sure all scenarios and border cases are considered.
		 */
		Sales mockedSalesDto = new Sales();
		mockedSalesDto.setProductCode("A");
		mockedSalesDto.setSaleAmount(BigDecimal.valueOf(7200));
		mockedSalesDto.setTxDate(LocalDate.of(2019,07,01));
		
		mockedSalesDtoList.add(mockedSalesDto);

		/** now return this mocked List **/
		doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());

		
		
		SystemException thrown =
		        Assertions.assertThrows(SystemException.class,
		           () -> tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId"),
		           "Expected doThing() to throw, but it didn't");
		
		    Assertions.assertTrue(thrown.getMessage().equalsIgnoreCase("No Discount Tier data Found"));

	}
	
	@Test
	@DisplayName("Negative System scenario : Should throw exception if no Tx Data is found based on user entered criteria  ")
	void tieredClaimNegativeScenarioNoTxDataFound() throws Exception {

		/** this is a critical piece - NOTE that we have commented off the autowiring of this service in this test of ours since 
		 * this service method is not called directly so if we want to mock such methods then we need to explcitly mock the class  
		 */
		/** this is the incoming request from UI to calculate **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("merch" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		/** mocking scenario where there is NO discount tier data which is bad and service method should throw an exception **/		
		
		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		/** mocking the discount tier data that would be retrieved from master table **/		
		DiscountTierDto mockDiscountTierDto1 = new DiscountTierDto(1,BigDecimal.valueOf(0),BigDecimal.valueOf(1000),BigDecimal.valueOf(2));
		DiscountTierDto mockDiscountTierDto2 = new DiscountTierDto(2,BigDecimal.valueOf(1001),BigDecimal.valueOf(5000),BigDecimal.valueOf(5));
		DiscountTierDto mockDiscountTierDto3 = new DiscountTierDto(3,BigDecimal.valueOf(5001),null,BigDecimal.valueOf(7.5));


		/** the mocked discount tier data should be returned when its service gets invoked 
		 *  this is how we are mocking out the db call and making this an isolated unit test
		 */
		Constants.discountTierList.clear();
		Constants.discountTierList.add(mockDiscountTierDto1);
		Constants.discountTierList.add(mockDiscountTierDto2);
		Constants.discountTierList.add(mockDiscountTierDto3);
		
		/** we now need to mock the call to actual data being made this is what we want to mock :
		 * 
		 * **/
		List<Sales> mockedSalesDtoList  = new ArrayList<>();
				
		/** now return this mocked empty List to ensure this scenario is triggered **/
		doReturn(mockedSalesDtoList).when(salesRepository).getDetails(any(),any());

		
		
		BusinessException thrown =
		        Assertions.assertThrows(BusinessException.class,
		           () -> tieredClaimService.calculateClaim(postClaimRequestDto,"xAppCorelationId"),
		           "Expected doThing() to throw, but it didn't");
		
		    Assertions.assertTrue(thrown.getMessage().equalsIgnoreCase("No data found for user defined criteria - Please change input data  !"));

	}




	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
