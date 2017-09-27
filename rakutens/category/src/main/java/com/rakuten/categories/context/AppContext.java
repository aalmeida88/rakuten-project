package com.rakuten.categories.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rakutten.utils.context.AppContextConfigurer;
import com.rakutten.utils.context.ExceptionControllerAdviceConfigurer;

@Configuration
@ComponentScan(basePackages = { Constants.BASE_PACKAGE, "com.rakuten.utils" })
public class AppContext extends AppContextConfigurer {

	@Bean
	public ExceptionControllerAdviceConfigurer controllerAdvice() {
		return new ExceptionControllerAdvice();
	}

}
