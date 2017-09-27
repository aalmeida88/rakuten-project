package com.rakuten.front.view.data;

public class Product {

	private String code;
	private String description;
	private Double eurPrice;
	private Category category;

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

	public Double getEurPrice() {
		return eurPrice;
	}

	public void setEurPrice(Double eurPrice) {
		this.eurPrice = eurPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
