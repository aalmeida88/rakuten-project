package com.rakuten.categories.api.exception;

public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String code) {
		super("The resource " + code + " already exists");
	}

}
