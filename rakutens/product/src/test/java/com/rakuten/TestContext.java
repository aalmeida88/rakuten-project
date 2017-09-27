package com.rakuten;

import java.io.IOException;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.mock.ProductDAOMock;
import com.rakuten.product.integration.category.CategoryClient;
import com.rakuten.product.integration.fixer.FixerClient;
import com.rakuten.product.persistance.dao.ProductDAO;

@Configuration
@ComponentScan(basePackages = {"com.rakuten.product.controller", "com.rakuten.product.component", "com.rakuten.product.persistance.mapper"})
@PropertySource(value = "classpath:test.properties", ignoreResourceNotFound = true)
public class TestContext {
	
	@Bean
    public ProductDAO productDAO() {
        return new ProductDAOMock();
    }
	
	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
	
	@Bean
	public CategoryClient categoryClient() throws IOException {
		CategoryClient mock = Mockito.mock(CategoryClient.class);
		
		Mockito.when(mock.categoryExists(TestUtils.CATEGORY_CODE_NON_EXISTENT))
        .thenReturn(false);
		
		Mockito.when(mock.categoryExists(TestUtils.CATEGORY_CODE_EXISTENT))
        .thenReturn(true);
		
		return mock;
	}
	
	@Bean
	public FixerClient fixerClient() throws IOException {
		FixerClient mock = Mockito.mock(FixerClient.class);
		
		Mockito.when(mock.getEurosExchangeRate())
        .thenReturn(TestUtils.FIXER_RESPONSE);
		
		return mock;
	}

}
