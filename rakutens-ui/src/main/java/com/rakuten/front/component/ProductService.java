package com.rakuten.front.component;

import com.rakuten.front.view.data.ProductsPage;

public interface ProductService {
	
	//order by name
	ProductsPage findProducts(int skip, int limit);

}
