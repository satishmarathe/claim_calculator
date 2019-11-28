package com.profectus.product.claim.calculator.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.exception.BusinessException;
import com.profectus.product.claim.calculator.exception.SystemException;
import com.profectus.product.claim.calculator.service.TieredClaimService;

@ExtendWith(SpringExtension.class)
/**
@SpringBootTest
@AutoConfigureMockMvc
**/
@WebMvcTest(controllers = TieredClaimController.class)
class TieredClaimControllerTest {

	@MockBean
	private TieredClaimService tieredClaimService;

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Positive Business scenario we should get back Response")	
	void tieredClaimPositiveScenario(String sourceType) throws Exception {
		// Setup mocked service
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));

		List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
		mockTieredClaimDtoList.add(mockTieredClaimDto);

		doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(any(),any());

		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		// Validate the response code and content type
		.andExpect(status().is(200))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].amount", is(400)));


	}
	
	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Negative data scenario we expect a 400 error for a null or empty start date")
	void tieredClaimNullOrEmptyFromDateScenario(String sourceType) throws Exception {
		// Setup mocked service
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , null,LocalDate.of(2019,07,02));

		TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));

		List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
		mockTieredClaimDtoList.add(mockTieredClaimDto);

		doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(any(),any());

		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		// Validate the response code and content type
		.andExpect(status().is(400))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message", is("FromDate cannot be null or empty")));

	}
	
	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Negative data scenario we expect a 400 error for a null source type")
	void tieredClaimNullSourceTypeScenario(String sourceType) throws Exception {
		// Setup mocked service
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(null , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));

		List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
		mockTieredClaimDtoList.add(mockTieredClaimDto);

		doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(any(),any());

		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		// Validate the response code and content type
		.andExpect(status().is(400))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message", is("The product source cannot be null or empty")));

	}
	
	@Test
	@DisplayName("POST /claim/tiered/calc - Negative data scenario we expect a 400 error for an invalid source type")
	void tieredClaimInvalidSourceTypeScenario() throws Exception {
		// Setup mocked service
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("abc" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));

		TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));

		List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
		mockTieredClaimDtoList.add(mockTieredClaimDto);

		doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(any(),any());

		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		// Validate the response code and content type
		.andExpect(status().is(400))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message", is("Invalid Product Source")));
		

	}
	
	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Negative Business scenario : we expect a 400 error in case of Start Date greater than End Date")
	void tieredClaimFromDateGreaterThanToDateScenario(String sourceType) throws Exception {
		// Setup mocked service
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto(sourceType , LocalDate.of(2019,07,02),LocalDate.of(2019,07,01));

		TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));

		List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
		mockTieredClaimDtoList.add(mockTieredClaimDto);

		doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(any(),any());

		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		// Validate the response code and content type
		.andExpect(status().is(400))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().string(org.hamcrest.Matchers.containsString("From Date should not be greater than To Date")));

	}
	
	@org.junit.Test(expected = BusinessException.class)
	@DisplayName("POST /claim/tiered/calc - Negative Business scenario : we expect a BusinessException to be thrown ")
	void tieredClaimNoDataFoundScenario() throws Exception {
		/** the request **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("sales" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));
		
		/** we are mocking an exception being thrown by service class - this exception is for no data being found **/
		when(tieredClaimService.calculateClaim(any(),any())).thenThrow(BusinessException.class);
		
		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON));		
	}
	
	@org.junit.Test(expected = SystemException.class)
	@DisplayName("POST /claim/tiered/calc - Negative Business scenario : we expect a SystemException to be thrown ")
	void tieredClaimNoTierDataScenario() throws Exception {
		/** the request **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("sales" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));
		
		/** we are mocking an exception being thrown by service class - this exception is for no data being found **/
		when(tieredClaimService.calculateClaim(any(),any())).thenThrow(SystemException.class);
		
		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON));

		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Negative Business scenario : we expect a 400 response when a BusinessException is thrown")
	void tieredClaimExpectStatus400WhenBusinessExceptionScenario() throws Exception {
		/** the request **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("sales" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));
		
		/** we are mocking an exception being thrown by service class - this exception is for no data being found **/
		when(tieredClaimService.calculateClaim(any(),any())).thenThrow(BusinessException.class);
		
		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		.andExpect(status().is(400));
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		//.andExpect(content().string(org.hamcrest.Matchers.containsString("From Date should not be greater than To Date")));
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"merch", "sales"})
	@DisplayName("POST /claim/tiered/calc - Negative Business scenario : we expect a 500 response when a SystemException is thrown")
	void tieredClaimExpectStatus500WhenSystemExceptionScenario() throws Exception {
		/** the request **/
		ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("sales" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));
		
		/** we are mocking an exception being thrown by service class - this exception is for no data being found **/
		when(tieredClaimService.calculateClaim(any(),any())).thenThrow(SystemException.class);
		
		mockMvc.perform(post("/claim/tiered/calc")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

		.andExpect(status().is(500));
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		//.andExpect(content().string(org.hamcrest.Matchers.containsString("From Date should not be greater than To Date")));
		
	}


	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
