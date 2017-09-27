package com.rakuten.categories.component;

import java.util.List;

import com.rakuten.categories.api.CategoryCreateDTO;
import com.rakuten.categories.api.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO create(CategoryCreateDTO dto);
	
	CategoryDTO update(CategoryCreateDTO dto);
	
	void delete(String categoryCode);
	
	List<CategoryDTO> getByCode(List<String> categoryCodes);
	
	boolean existCategory(String categoryCode);

}
