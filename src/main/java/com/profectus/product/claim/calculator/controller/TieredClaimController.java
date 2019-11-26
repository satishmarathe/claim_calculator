package com.profectus.product.claim.calculator.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.profectus.product.claim.calculator.common.ErrorResponse;
import com.profectus.product.claim.calculator.common.ProductSourceType;
import com.profectus.product.claim.calculator.dto.ClaimRequestDto;
import com.profectus.product.claim.calculator.service.TieredClaimService;



@RestController
@Validated
@RequestMapping(path="/claim")
public class TieredClaimController {

	/** TODO - use proper logging **/
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private TieredClaimService tieredClaimService;

	@CrossOrigin
	@GetMapping(path="/tiered/calc")

	public ResponseEntity<?> calculateTieredClaim(
			@RequestParam(name = "prodSource") @NotEmpty(message = "The product source cannot be null or empty")   String typeId, 
			@RequestParam (name = "fromDate") @NotEmpty(message = "FromDate cannot be null or empty") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate , 
			@RequestParam (name = "toDate") @NotEmpty(message = "ToDate cannot be null or empty") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

		/** TODO - need validation framework **/
		/** TODO - need exception handling **/
		/** TODO - need to pass and capture query parameters **/
		try {
			System.out.println("<<<< in controller calculateTieredClaim  >>>> " + typeId + fromDate + toDate );
			/**TODO pass proper parameters **/
			return ResponseEntity.status(200).body(tieredClaimService.calculateClaim(typeId,fromDate,toDate));
		}catch(Exception e) {
			e.printStackTrace();
			/**TODO need proper exception handling **/
		}
		return null;

	}

	@CrossOrigin
	@PostMapping(path="/tiered/calc")	
	public ResponseEntity<?> getTieredClaim(@Valid @RequestBody ClaimRequestDto claimRequestDto) {

		/** TODO - need validation framework **/
		/** TODO - need exception handling **/
		/** TODO - need to pass and capture query parameters **/
		try {
			System.out.println("<<<< in controller calculateTieredClaim  >>>> " + claimRequestDto );
			/**TODO pass proper parameters **/
			return ResponseEntity.status(200).body(tieredClaimService.calculateClaim(claimRequestDto.getProductTypeSource(),claimRequestDto.getFromDate(),claimRequestDto.getToDate()));
		}catch(Exception e) {
			e.printStackTrace();
			/**TODO need proper exception handling **/
		}
		return null;

	}
	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleException(MethodArgumentNotValidException exception) {
		System.out.println(" here ");
		String errorMsg = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.findFirst()
				.orElse(exception.getMessage());
		
		//return ErrorResponse.builder().message(errorMsg).build();
		return new ErrorResponse(errorMsg);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(InvalidFormatException exception)  {
    	System.out.println(" ");
    	System.out.println("<<< inside invalidFormatException >>>>");
    	System.out.println(" ");
    	
    	return new ErrorResponse("Date fields should be of the following format: YYYY-mm-dd");
    }
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(UnexpectedTypeException exception)  {
    	exception.printStackTrace();
		System.out.println(" ");
    	System.out.println("<<< inside UnexpectedTypeException >>>>");
    	System.out.println(" ");
    	
    	return new ErrorResponse("Date field should not be null or empty ");
    }
	
}















