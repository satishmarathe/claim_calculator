package com.profectus.product.claim.calculator.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MerchRepository extends CrudRepository<Merch, Integer> {

	@Query(
			value = "SELECT product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\") as tx_date,SUM(purchase_amount) as purchase_amount \r\n" + 
					"from merch\r\n" + 
					"WHERE tx_date BETWEEN ?1 AND ?2 \r\n" + 
					"GROUP BY product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\")",  
					nativeQuery = true)
	List<Merch> getDetails(LocalDateTime startDateTime,LocalDateTime endDateTime);
}
