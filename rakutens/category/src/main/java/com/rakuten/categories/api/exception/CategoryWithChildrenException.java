package com.rakuten.categories.api.exception;

public class CategoryWithChildrenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoryWithChildrenException(String code) {
		super("The resource " + code + " have children");
	}

}
