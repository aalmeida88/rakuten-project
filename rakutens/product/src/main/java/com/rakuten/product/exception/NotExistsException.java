package com.rakuten.product.exception;

public class NotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotExistsException(String code) {
		super("The resource " + code + " does not exists");
	}

}
