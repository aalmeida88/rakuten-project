package com.rakuten;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.rakuten.product.integration.fixer.FixerResponseDTO;
import com.rakuten.product.persistance.model.CurrencyCodes;

public class TestUtils {
	
	public static final String CATEGORY_CODE_NON_EXISTENT = "category-not-exists";
	public static final String CATEGORY_CODE_EXISTENT = "category-exists";
	
	public static final FixerResponseDTO FIXER_RESPONSE = new FixerResponseDTO();
	static{
		Map<String, BigDecimal> rates = new HashMap<>();
		rates.put(CurrencyCodes.US_DOLLARS, BigDecimal.valueOf(1.3));
		FIXER_RESPONSE.setRates(rates);
	}

}
