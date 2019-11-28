package com.profectus.product.claim.calculator.controller;

import java.util.List;

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
import com.profectus.product.claim.calculator.dto.TieredClaimDto;
import com.profectus.product.claim.calculator.exception.BusinessException;
import com.profectus.product.claim.calculator.exception.SystemException;
import com.profectus.product.claim.calculator.service.TieredClaimService;

import org.springframework.http.HttpStatus;



@RestController
@RequestMapping(path="/claim")
@Validated
public class TieredClaimController {

	/** TODO - use proper logging **/
	private static final Logger LOGGER = LogManager.getLogger(TieredClaimController.class);

	@Autowired
	private TieredClaimService tieredClaimService;

	

	@CrossOrigin
	@PostMapping(path="/tiered/calc")	
	public ResponseEntity<?> getTieredClaim(@Valid @RequestBody ClaimRequestDto claimRequestDto) {
		final String METHOD_NAME = "getTieredClaim";
		
		LOGGER.info(" {} | {} | Entering input data is  ...  {} ",METHOD_NAME,"xAppCorelationId",claimRequestDto);
		
		List<TieredClaimDto> tieredClaimResponseList = null;
		try {		
			LOGGER.info(" {} | {} |About to call service ",METHOD_NAME,"xAppCorelationId");
			tieredClaimResponseList = tieredClaimService.calculateClaim(claimRequestDto,"xAppCorelationId");
			
			LOGGER.info(" {} | {} | Exiting successfully with following data ... {}",METHOD_NAME,"xAppCorelationId",tieredClaimResponseList);
			return ResponseEntity.status(200).body(tieredClaimResponseList);
		}catch (BusinessException be) {
			LOGGER.error("{} , {} END , getTieredClaim  {} , Errored out with BusinessException {}",METHOD_NAME,"xAppCorelationId",claimRequestDto,be);
			/** these are the validation exceptions caught in PL/SQL **/
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(be.getMessage()));			
		}catch (SystemException se) {
			LOGGER.error("{} , {} END , getTieredClaim  {} , Errored out with SystemException {}",METHOD_NAME,"xAppCorelationId",claimRequestDto,se);
			/** these are the validation exceptions caught in PL/SQL **/
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(se.getMessage()));			
		}catch(Exception e) {
			/** this will catch any unexpected exceptions and also if DAO throws SQLException which will come as a runtime exception **/
			LOGGER.error("{} , {} END , getTieredClaim with params: {} , Errored out with Unknown Exception {}",METHOD_NAME,"xAppCorelationId",claimRequestDto,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("System Exception , Please contact System Administrator");
		}

	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleException(MethodArgumentNotValidException exception) {
		final String METHOD_NAME = "handleException";
		LOGGER.info(" {} | {} | Entering handler handling MethodArgumentNotValidException   ...  ",METHOD_NAME,"xAppCorelationId");
		/**
		 * when : date is null comes here ( not null validator )
		 * when : source is invalid ( custom validator ) 
		 * when : source is null ( not empty validator )
		 */
		String errorMsg = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.findFirst()
				.orElse(exception.getMessage());
		
		LOGGER.info(" {} | {} | Exiting error message is  ... {}",METHOD_NAME,"xAppCorelationId",errorMsg);
		//return ErrorResponse.builder().message(errorMsg).build();
		return new ErrorResponse(errorMsg);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(InvalidFormatException exception)  {
		final String METHOD_NAME = "handleException";
		LOGGER.info(" {} | {} | Entering handler handling InvalidFormatException   ...   ",METHOD_NAME,"xAppCorelationId");
		/**
		 * invalid date format comes here 
		 */
    	String errorMsg = "Date fields should be of the following format: YYYY-mm-dd";
    	LOGGER.info(" {} | {} | Exiting  error message is  ... {}",METHOD_NAME,"xAppCorelationId",errorMsg);
    	return new ErrorResponse(errorMsg);
    }
	
	
	
	
	
	
}















