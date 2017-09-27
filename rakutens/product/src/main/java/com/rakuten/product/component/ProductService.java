package com.rakuten.product.component;

import com.rakuten.product.api.ProductPageDTO;
import com.rakuten.product.api.ProductDTO;

public interface ProductService {
	
	ProductDTO create(ProductDTO dto);
	
	void update(ProductDTO dto);
	
	void delete(String categoryCode);
	
	ProductPageDTO list(int page, int size);
	
	ProductDTO getByCode(String productCode);
	
}
