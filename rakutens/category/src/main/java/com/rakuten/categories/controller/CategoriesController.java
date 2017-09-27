package com.rakuten.categories.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.categories.api.CategoryCreateDTO;
import com.rakuten.categories.api.CategoryDTO;
import com.rakuten.categories.api.CategoryExistsDTO;
import com.rakuten.categories.component.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoriesController {

	@Autowired
	private CategoryService service;

	@RequestMapping(method = RequestMethod.GET, value = "/{categoryCodes}")
	public List<CategoryDTO> get(@PathVariable String[] categoryCodes) {
		return service.getByCode(Arrays.asList(categoryCodes));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{categoryCode}/exists")
	public CategoryExistsDTO exists(@PathVariable String categoryCode) {
		return new CategoryExistsDTO(service.existCategory(categoryCode));
	}

	@RequestMapping(method = RequestMethod.POST)
	public CategoryDTO create(@RequestBody CategoryCreateDTO dto) {
		return service.create(dto);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public CategoryDTO update(@RequestBody CategoryCreateDTO dto) {
		return service.update(dto);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{categoryCode}")
	public void delete(@PathVariable String categoryCode) {
		service.delete(categoryCode);
	}

}
