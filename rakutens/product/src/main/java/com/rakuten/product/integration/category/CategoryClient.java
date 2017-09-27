package com.rakuten.product.integration.category;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rakutten.utils.integration.RestConnector;

@Component
public class CategoryClient extends RestConnector {

	private static final TypeReference<CategoryExistsDTO> EXISTS_RESPONSE_TYPE_REF = new TypeReference<CategoryExistsDTO>() {
	};

	private static final String EXISTS_SERVICE_PATH = "exists";

	@Value("${integration.category.baseUrl}")
	private String baseUrl;

	public boolean categoryExists(String code) throws IOException {
		String url = String.format("%s/%s/%s", baseUrl, code, EXISTS_SERVICE_PATH);
		CategoryExistsDTO existsDTO = doGet(url, EXISTS_RESPONSE_TYPE_REF);
		return existsDTO.isExists();
	}

}
