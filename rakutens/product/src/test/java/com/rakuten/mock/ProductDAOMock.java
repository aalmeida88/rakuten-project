package com.rakuten.mock;

import com.rakuten.product.persistance.dao.ProductDAO;
import com.rakuten.product.persistance.model.Product;

public class ProductDAOMock extends MongoRepositoryAndPageableMock<Product> implements ProductDAO  {

	@Override
	protected String getId(Product t) {
		return t.getCode();
	}
	
}
