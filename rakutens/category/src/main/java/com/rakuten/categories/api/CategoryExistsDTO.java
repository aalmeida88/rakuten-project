package com.rakuten.categories.api;

public class CategoryExistsDTO {
	
	private final boolean exists;
	
	public CategoryExistsDTO(boolean exists) {
		this.exists = exists;
	}

	public boolean isExists() {
		return exists;
	}
}
