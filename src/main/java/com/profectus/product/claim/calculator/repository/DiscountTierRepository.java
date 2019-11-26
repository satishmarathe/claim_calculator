package com.profectus.product.claim.calculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountTierRepository extends JpaRepository<DiscountTier, Integer> {
	public List<DiscountTier> findAllByOrderByTierIdAsc();
}
