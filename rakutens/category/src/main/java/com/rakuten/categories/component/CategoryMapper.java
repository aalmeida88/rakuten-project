package com.rakuten.categories.component;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.rakuten.categories.api.CategoryDTO;
import com.rakuten.categories.presistance.model.Category;

@Component
public class CategoryMapper {

	public CategoryDTO map(Category c) {
		if (c == null) {
			return null;
		}
		CategoryDTO dto = new CategoryDTO();
		dto.setCode(c.getCode());
		dto.setDescription(c.getDescription());
		dto.setAncestors(new ArrayList<>(c.getAncestors()));
		return dto;
	}

	public Category map(CategoryDTO dto) {
		if (dto == null) {
			return null;
		}
		Category c = new Category();
		c.setCode(dto.getCode());
		c.setDescription(dto.getDescription());
		c.setAncestors(new ArrayList<>(dto.getAncestors()));
		return c;
	}

}
