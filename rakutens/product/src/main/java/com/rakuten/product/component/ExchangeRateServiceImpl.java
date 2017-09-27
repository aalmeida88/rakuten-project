package com.rakuten.product.component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rakuten.product.api.AmountDTO;
import com.rakuten.product.exception.UnsuportedCurrencyException;
import com.rakuten.product.integration.fixer.FixerClient;
import com.rakuten.product.integration.fixer.FixerResponseDTO;

@Component
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private FixerClient fixerClient;

	//to be thread safe use volatile
	private volatile FixerResponseDTO fixerResponseDTO;

	@PostConstruct
	private void init() throws IOException {
		//start with cached rates
		this.updateRates();
	}
	
	@Scheduled(cron = "${integration.exchangeRates.update.cron}")
	public void updateRates() throws IOException{
		this.fixerResponseDTO = this.fixerClient.getEurosExchangeRate();
	}

	@Override
	public BigDecimal convertToEuros(AmountDTO dto) {
		Map<String, BigDecimal> rates = this.fixerResponseDTO.getRates();
		if (!rates.containsKey(dto.getCurrencyCode())) {
			throw new UnsuportedCurrencyException(dto.getCurrencyCode());
		}
		return dto.getAmount().divide(rates.get(dto.getCurrencyCode()),2, BigDecimal.ROUND_HALF_UP);
	}

}
