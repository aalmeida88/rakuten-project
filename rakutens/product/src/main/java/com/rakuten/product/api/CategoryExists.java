package com.rakuten.product.api;

public class CategoryExists {
	
	private final boolean exists;
	
	public CategoryExists(boolean exists) {
		this.exists = exists;
	}

	public boolean isExists() {
		return exists;
	}
}
