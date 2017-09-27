package com.rakuten.product.persistance.mapper;

import org.springframework.stereotype.Component;

import com.rakuten.product.api.AmountDTO;
import com.rakuten.product.api.ProductDTO;
import com.rakuten.product.persistance.model.CurrencyCodes;
import com.rakuten.product.persistance.model.Product;

@Component
public class ProductMapper {

	public ProductDTO map(Product p) {
		if (p == null) {
			return null;
		}
		
		AmountDTO priceDTO = new AmountDTO();
		priceDTO.setCurrencyCode(CurrencyCodes.EUROS);
		priceDTO.setAmount(p.getEurPrice());
		
		ProductDTO dto = new ProductDTO();
		dto.setCode(p.getCode());
		dto.setDescription(p.getDescription());
		dto.setCategoryCode(p.getCategoryCode());
		dto.setPrice(priceDTO);
		
		return dto;
	}

	public Product map(ProductDTO dto, AmountDTO price) {
		if (dto == null) {
			return null;
		}
		assert(CurrencyCodes.EUROS.equals(price.getCurrencyCode()));
		Product p = new Product();
		p.setCode(dto.getCode());
		p.setDescription(dto.getDescription());
		p.setEurPrice(price.getAmount());
		p.setCategoryCode(dto.getCategoryCode());
		return p;
	}

}
