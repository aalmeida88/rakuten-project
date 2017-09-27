package com.rakuten.product.persistance.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public class Product{
	
	@Id
	private String code;
	private String description;
	private BigDecimal eurPrice;
	private String categoryCode;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEurPrice() {
		return eurPrice;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setEurPrice(BigDecimal eurPrice) {
		this.eurPrice = eurPrice;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
