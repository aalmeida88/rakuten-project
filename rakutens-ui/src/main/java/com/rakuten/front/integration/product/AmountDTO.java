package com.rakuten.front.integration.product;

public class AmountDTO {

	private String currencyCode;
	private Double amount;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
