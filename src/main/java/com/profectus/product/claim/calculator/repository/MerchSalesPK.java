package com.profectus.product.claim.calculator.repository;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class MerchSalesPK implements Serializable {
	

	private String productCode;
	private LocalDate txDate;
 
    public MerchSalesPK() {    	
    }
 
    public MerchSalesPK(String productCode, LocalDate txDate) {
        this.productCode = productCode;
        this.txDate = txDate;
    }
 
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((txDate == null) ? 0 : txDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerchSalesPK other = (MerchSalesPK) obj;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (txDate == null) {
			if (other.txDate != null)
				return false;
		} else if (!txDate.equals(other.txDate))
			return false;
		return true;
	}
}
