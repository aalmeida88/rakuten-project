package com.rakuten.categories;

import com.rakuten.categories.context.AppContext;
import com.rakuten.categories.context.Constants;
import com.rakutten.utils.WebServerContract;

public class CategoriesWebServer extends WebServerContract {

	public CategoriesWebServer(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws Exception {
		CategoriesWebServer server = new CategoriesWebServer(args);
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
