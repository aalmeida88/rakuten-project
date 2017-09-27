package com.rakuten.front.integration.product;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rakuten.front.integration.RestConnector;

@Component
public class ProductServiceClient extends RestConnector {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClient.class);
	
	private static final TypeReference<ProductPageDTO> PRODUCT_PAGE_TYPE_REF = new TypeReference<ProductPageDTO>() {
	};

	private static final String LIST_SERVICE_PATH = "products";

	@Value("${integration.product.baseUrl}")
	private String baseUrl;

	public ProductPageDTO list(int page, int pageSize) throws IOException {
		LOGGER.info("Calling products service for: {} - {}", page, pageSize);
		String url = String.format("%s/%s/%d/%d", baseUrl, LIST_SERVICE_PATH, page, pageSize);
		return this.doGet(url, PRODUCT_PAGE_TYPE_REF);
	}

}
