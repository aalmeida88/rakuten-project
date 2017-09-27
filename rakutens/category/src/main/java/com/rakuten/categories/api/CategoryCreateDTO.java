package com.rakuten.categories.api;

public class CategoryCreateDTO {

	private String code;
	private String description;
	private String parentCode;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
