package com.rakuten.product.integration.fixer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rakutten.utils.integration.RestConnector;

@Component
public class FixerClient extends RestConnector {
	
	private static final TypeReference<FixerResponseDTO> FIXER_RESPONSE_TYPE_REF = new TypeReference<FixerResponseDTO>() {};
	
	@Value("${integration.fixer.url}")
	private String fixerUrl;
	
	public FixerResponseDTO getEurosExchangeRate() throws IOException {
		return doGet(fixerUrl, FIXER_RESPONSE_TYPE_REF);
	}

}
