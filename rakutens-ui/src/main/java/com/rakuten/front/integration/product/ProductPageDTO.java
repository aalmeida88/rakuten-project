package com.rakuten.front.integration.product;

import java.util.List;

public class ProductPageDTO {

	private int totalProducts;
	private List<ProductDTO> products;

	public int getTotalProducts() {
		return totalProducts;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
