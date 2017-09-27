package com.rakuten.front.integration.product;

public class ProductDTO {

	private String code;
	private String description;
	private AmountDTO price;
	private String categoryCode;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public AmountDTO getPrice() {
		return price;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(AmountDTO price) {
		this.price = price;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
