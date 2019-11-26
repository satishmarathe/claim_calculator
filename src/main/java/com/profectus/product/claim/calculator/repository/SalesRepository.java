package com.profectus.product.claim.calculator.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**TODO need to understand Generic type second parameter what should it's type be ? **/ 
public interface SalesRepository extends JpaRepository<Sales, Integer> {
	@Query(
			  value = "SELECT product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\"),SUM(sale_amount) \r\n" + 
			  		"from sales\r\n" + 
			  		"WHERE tx_date BETWEEN '2019-07-07' AND '2019-09-09'\r\n" + 
			  		"GROUP BY product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\")", 
			  nativeQuery = true)
    List<Sales> getDetails1();
	
	@Query(
			  value = "SELECT product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\") as tx_date,SUM(sale_amount) as sale_amount \r\n" + 
			  		"from sales\r\n" + 
			  		"WHERE tx_date BETWEEN ?1 AND ?2 \r\n" + 
			  		"GROUP BY product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\")",  
			  nativeQuery = true)
List<Sales> getDetails(LocalDateTime startDateTime,LocalDateTime endDateTime);
	
	@Query(
			  value = "SELECT product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\") as tx_date,SUM(sale_amount) as sale_amount \r\n" + 
			  		"from sales\r\n" + 
			  		"WHERE tx_date BETWEEN '2019-07-07' AND '2019-09-09'\r\n" + 
			  		"GROUP BY product_code,DATE_FORMAT(tx_date, \"%Y-%m-01\")", 
			  nativeQuery = true)
  List<Sales> getDetailsWorks2();
	
	@Query(
			  value = "SELECT product_code,tx_date,SUM(sale_amount) as sale_amount \r\n" + 
			  		"from claims.sales\r\n" + 
			  		"WHERE tx_date BETWEEN '2019-07-07' AND '2019-09-09'\r\n" + 
			  		"GROUP BY product_code,tx_date;", 
			  nativeQuery = true)
  List<Sales> getDetailsWorks1();
}
