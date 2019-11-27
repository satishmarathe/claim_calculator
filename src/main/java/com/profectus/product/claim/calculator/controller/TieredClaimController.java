package com.profectus.product.claim.calculator.controller;

import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.profectus.product.claim.calculator.common.ErrorResponse;
import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.service.TieredClaimService;

import org.springframework.http.HttpStatus;



@RestController
@RequestMapping(path="/claim")
@Validated
public class TieredClaimController {

	/** TODO - use proper logging **/
	private static final Logger logger = LogManager.getLogger(TieredClaimController.class);

	@Autowired
	private TieredClaimService tieredClaimService;

	

	@CrossOrigin
	@PostMapping(path="/tiered/calc")	
	public ResponseEntity<?> getTieredClaim(@Valid @RequestBody ClaimRequestDto claimRequestDto) {

		/** TODO - need validation framework **/
		/** TODO - need exception handling **/
		/** TODO - need to pass and capture query parameters **/
		try {
			System.out.println("<<<< in controller calculateTieredClaim  >>>> " + claimRequestDto );
			/**TODO pass proper parameters **/
			return ResponseEntity.status(200).body(tieredClaimService.calculateClaim(claimRequestDto));
		}catch(Exception e) {
			e.printStackTrace();
			/**TODO need proper exception handling **/
		}
		return null;

	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleException(MethodArgumentNotValidException exception) {
		System.out.println(" ");
		System.out.println(" <<< inside MethodArgumentNotValidException >>> ");
		System.out.println(" ");
		
		/**
		 * when : date is null comes here ( not null validator )
		 * when : source is invalid ( custom validator ) 
		 * when : source is null ( not empty validator )
		 */
		String errorMsg = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.findFirst()
				.orElse(exception.getMessage());
		System.out.println("error msg = " + errorMsg);
		//return ErrorResponse.builder().message(errorMsg).build();
		return new ErrorResponse(errorMsg);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(InvalidFormatException exception)  {
		/**
		 * invalid date format comes here 
		 */
    	System.out.println(" ");
		System.out.println(" <<< inside InvalidFormatException >>> ");
		System.out.println(" ");
    	
    	return new ErrorResponse("Date fields should be of the following format: YYYY-mm-dd");
    }
	
	
	
	
	
	
}















