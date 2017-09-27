package com.rakuten.product.exception;

public class MandatoryFieldException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MandatoryFieldException(String mandatoryField){
		super("The field '"+mandatoryField+"'is mandatory");
	}

}
