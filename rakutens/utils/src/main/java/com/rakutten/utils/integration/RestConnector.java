package com.rakutten.utils.integration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestConnector {

	@Autowired
	private ObjectMapper objectMapper;

	private OkHttpClient client = new OkHttpClient();

	protected <T> T doGet(String url, TypeReference<T> typeReference) throws IOException {
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		
		return objectMapper.readValue(response.body().string(), typeReference);
	}

}
