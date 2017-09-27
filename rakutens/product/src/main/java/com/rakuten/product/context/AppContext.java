package com.rakuten.product.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rakutten.utils.context.AppContextConfigurer;
import com.rakutten.utils.context.ExceptionControllerAdviceConfigurer;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = { Constants.BASE_PACKAGE, "com.rakuten.utils" })
public class AppContext extends AppContextConfigurer {

	@Bean
	public ExceptionControllerAdviceConfigurer controllerAdvice() {
		return new ExceptionControllerAdvice();
	}

}