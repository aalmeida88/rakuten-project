package com.rakuten.front.integration.category;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rakuten.front.integration.RestConnector;

@Component
public class CategoryServiceClient extends RestConnector {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceClient.class);
	
	private static final TypeReference<List<CategoryDTO>> CATEGORY_TYPE_REF = new TypeReference<List<CategoryDTO>>() {
	};

	private static final String GET_SERVICE_PATH = "category";

	@Value("${integration.category.baseUrl}")
	private String baseUrl;

	public CategoryDTO getCategory(String categoryCode) throws IOException {
		List<CategoryDTO> categories = this.getCategories(Arrays.asList(categoryCode));
		return categories.isEmpty() ? null: categories.get(0);
	}

	public List<CategoryDTO> getCategories(Collection<String> categoryCodes) throws IOException {
		String categoryCodesParam = StringUtils.join(categoryCodes, ",");
		
		LOGGER.info("Getting categories from service with codes: {}", categoryCodesParam);
		
		String url = String.format("%s/%s/%s", baseUrl, GET_SERVICE_PATH, categoryCodesParam);
		return this.doGet(url, CATEGORY_TYPE_REF);
	}

}
