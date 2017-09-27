package com.rakuten.front.view.data;

import java.util.List;

public class Category {

	private String code;
	private String description;
	private List<Category> ancestors;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getAncestors() {
		return ancestors;
	}

	public void setAncestors(List<Category> ancestors) {
		this.ancestors = ancestors;
	}

}
