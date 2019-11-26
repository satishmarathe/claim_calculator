package com.profectus.product.claim.calculator.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.service.TieredClaimService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TieredClaimControllerTest {
    //@MockBean
    //private ProductService service;
    
    @MockBean
	private TieredClaimService tieredClaimService;

    @Autowired
    private MockMvc mockMvc;

   

   

    @Test
    @DisplayName("POST /product - Success")
    void testCreateProduct() throws Exception {
        // Setup mocked service
    	ClaimRequestDto postClaimRequestDto = new ClaimRequestDto("merch" , LocalDate.of(2019,07,02),LocalDate.of(2019,07,02));
    	TieredClaimDto mockTieredClaimDto = new TieredClaimDto("A",LocalDate.of(2019,07,01),new BigDecimal(400),new BigDecimal(8));
    	
    	List <TieredClaimDto> mockTieredClaimDtoList = new ArrayList<>();
    	mockTieredClaimDtoList.add(mockTieredClaimDto);
    	
    	System.out.println(">>>> getProductTypeSource = <<<<<< " + postClaimRequestDto.getProductTypeSource());
    	System.out.println(">>>> from date = <<<<<< " + postClaimRequestDto.getFromDate());
    	System.out.println(">>>> to date = <<<<<< " + postClaimRequestDto.getToDate());
        //doReturn(mockTieredClaimDto).when(tieredClaimService).save(any());
        doReturn(mockTieredClaimDtoList).when(tieredClaimService).calculateClaim(postClaimRequestDto.getProductTypeSource(), postClaimRequestDto.getFromDate(), postClaimRequestDto.getToDate());

        mockMvc.perform(post("/claim/tiered/calc")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(postClaimRequestDto)).accept(MediaType.APPLICATION_JSON))

                // Validate the response code and content type
                .andExpect(status().is(200));
        		  //.andExpect(status().isBadRequest()).andReturn();
        //System.out.println(">>>>>> = " + result.getResponse().getContentAsString());
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                //.andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                //.andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))

                // Validate the returned fields
                //.andExpect(jsonPath("$.id", is(1)))
                //.andExpect(jsonPath("$.name", is("Product Name")))
                //.andExpect(jsonPath("$.quantity", is(10)))
                //.andExpect(jsonPath("$.version", is(1)));
    }

    

   

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
