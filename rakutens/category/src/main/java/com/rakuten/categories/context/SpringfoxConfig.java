package com.rakuten.categories.context;

import org.springframework.context.annotation.Configuration;

import com.rakutten.utils.context.springfox.SpringfoxConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@Configuration
public class SpringfoxConfig extends SpringfoxConfigurer {

	@Override
	protected ApiInfo apiInfo() {
		return new ApiInfoBuilder().contact(new Contact("gmail", "", "aalmeida88@gmail.com")).title("Categories API")
				.description("Categories Services API").build();
	}

	@Override
	protected String basePackage() {
		return Constants.BASE_PACKAGE;
	}

}
