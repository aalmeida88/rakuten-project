package com.rakuten.front.component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rakuten.front.integration.product.ProductDTO;
import com.rakuten.front.integration.product.ProductPageDTO;
import com.rakuten.front.integration.product.ProductServiceClient;
import com.rakuten.front.view.data.Category;
import com.rakuten.front.view.data.Product;
import com.rakuten.front.view.data.ProductsPage;

@Component
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductServiceClient productClient;

	@Autowired
	private CategoryService categoryService;

	@Override
	public ProductsPage findProducts(int skip, int limit) {

		ProductPageDTO productPageDTO;

		int page = skip / limit + 1;// 0
		try {
			productPageDTO = productClient.list(page, limit);
		} catch (IOException e) {
			LOGGER.error("An error ocurred trying to get products...");
			// on error return empty data
			return new ProductsPage(0, Collections.emptyList());
		}

		List<Product> list = productPageDTO.getProducts().stream().map(this::map).collect(Collectors.toList());

		return new ProductsPage(productPageDTO.getTotalProducts(), list);
	}

	private Product map(ProductDTO dto) {
		Category c = categoryService.getCategory(dto.getCategoryCode());
		Product p = new Product();
		p.setCode(dto.getCode());
		p.setDescription(dto.getDescription());
		p.setEurPrice(dto.getPrice().getAmount());
		p.setCategory(c);
		return p;
	}

}
