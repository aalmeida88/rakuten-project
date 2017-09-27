package com.rakuten.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class WebConfig {

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(this.getModule());
		// objectMapper.registerModule(new AfterburnerModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return objectMapper;
	}

	protected MappingJackson2HttpMessageConverter buildSnakeJackson2HttpMessageConverter() {
		// Jackson Http Converter
		MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

		jacksonConverter.setObjectMapper(getObjectMapper());
		return jacksonConverter;
	}

	protected SimpleModule getModule() {
		// Register custom serializers not needed
		SimpleModule module = new SimpleModule("DefaultModule", new Version(0, 0, 1, null, null, null));

		return module;
	}

}
