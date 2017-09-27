package com.rakuten.front.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.rakuten.front.component.ProductService;
import com.rakuten.front.view.data.Product;
import com.rakuten.front.view.data.ProductsPage;

public class LazyProductDataModel extends LazyDataModel<Product> {

	private static final long serialVersionUID = 1L;

	private final ProductService service;

	private Map<String, Product> lastProductsMap = Collections.emptyMap();

	public LazyProductDataModel(ProductService service) {
		this.service = service;
	}

	@Override
	public Product getRowData(String productCode) {
		return lastProductsMap.get(productCode);
	}

	@Override
	public Object getRowKey(Product product) {
		return product.getCode();
	}

	@Override
	public List<Product> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		ProductsPage productsData = service.findProducts(first, pageSize);

		List<Product> products = productsData.getProducts();
		
		this.setRowCount(productsData.getTotal());
		
		this.lastProductsMap = products.stream().collect(HashMap::new, (m, p) -> m.put(p.getCode(), p), Map::putAll);

		return products;
	}

}
