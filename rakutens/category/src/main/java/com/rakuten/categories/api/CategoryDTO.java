package com.rakuten.categories.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CategoryDTO {

	private String code;
	private String description;
	private List<String> ancestors = new ArrayList<>();
	
	@JsonIgnore
	public String getParentCode(){
		return ancestors.isEmpty() ? null : ancestors.get(0);
	}

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
