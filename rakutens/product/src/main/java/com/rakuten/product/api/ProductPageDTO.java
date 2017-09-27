package com.rakuten.product.api;

import java.util.List;

public class ProductPageDTO {

	private long totalProducts;
	private List<ProductDTO> products;

	public long getTotalProducts() {
		return totalProducts;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
