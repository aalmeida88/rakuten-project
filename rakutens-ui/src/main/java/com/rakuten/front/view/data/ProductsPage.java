package com.rakuten.front.view.data;

import java.util.List;

public class ProductsPage {

	private final int total;
	private final List<Product> products;

	public ProductsPage(int total, List<Product> products) {
		super();
		this.total = total;
		this.products = products;
	}

	public int getTotal() {
		return total;
	}

	public List<Product> getProducts() {
		return products;
	}

}
