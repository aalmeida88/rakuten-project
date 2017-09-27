package com.rakuten.product.component;

import java.math.BigDecimal;

import com.rakuten.product.api.AmountDTO;

public interface ExchangeRateService {
	
	BigDecimal convertToEuros(AmountDTO dto);

}
