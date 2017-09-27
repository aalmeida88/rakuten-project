package com.rakuten.product.exception;

public class UnsuportedCurrencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsuportedCurrencyException(String currencyCode) {
		super("Currency with code " + currencyCode + " not upported");
	}

}
