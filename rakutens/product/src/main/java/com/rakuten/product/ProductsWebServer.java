package com.rakuten.product;

import com.rakuten.product.context.AppContext;
import com.rakuten.product.context.Constants;
import com.rakutten.utils.WebServerContract;

public class ProductsWebServer extends WebServerContract {

	public ProductsWebServer(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws Exception {
		ProductsWebServer server = new ProductsWebServer(args);
		server.run();
	}

	@Override
	protected String appName() {
		return Constants.APP_NAME;
	}

	@Override
	protected Class<?> contextClazz() {
		return AppContext.class;
	}

	@Override
	protected String appContextPath() {
		return Constants.CONTEXT_PATH;
	}

}
