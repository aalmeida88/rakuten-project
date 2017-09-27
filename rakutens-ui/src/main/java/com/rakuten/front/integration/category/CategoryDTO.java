package com.rakuten.front.integration.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {

	private String code;
	private String description;
	private List<String> ancestors = new ArrayList<>();
	
	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getAncestors() {
		return ancestors;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAncestors(List<String> ancestors) {
		this.ancestors = ancestors;
	}

}
