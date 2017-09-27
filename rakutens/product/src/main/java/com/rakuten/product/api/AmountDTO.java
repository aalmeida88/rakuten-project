package com.rakuten.product.api;

import java.math.BigDecimal;

public class AmountDTO {

	private String currencyCode;
	private BigDecimal amount;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
