package com.rakuten.product.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.product.api.ProductDTO;
import com.rakuten.product.api.ProductPageDTO;
import com.rakuten.product.component.ProductService;
import com.rakuten.product.exception.MandatoryFieldException;
import com.rakuten.product.exception.NotExistsException;

@RestController
public class ProductController {

	@Value("${catalog.pageSize.default:10}")
	private int defaultPageSize;

	@Autowired
	private ProductService service;

	@RequestMapping(method = RequestMethod.GET, value = "/products")
	public ProductPageDTO list() {
		return service.list(0, defaultPageSize);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/products/{page}/{pageSize}")
	public ProductPageDTO list(@PathVariable int page, @PathVariable int pageSize) {
		return service.list(page, pageSize);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/product/{productCode}")
	public ProductDTO get(@PathVariable String productCode) {
		ProductDTO categoryDTO = service.getByCode(productCode);
		if (categoryDTO == null) {
			throw new NotExistsException(productCode);
		}
		return categoryDTO;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/product")
	public ProductDTO create(@RequestBody ProductDTO dto) {
		//simple validations
		if (dto.getPrice() == null) {
			throw new MandatoryFieldException("price");
		}
		
		if(dto.getPrice().getCurrencyCode() == null){
			throw new MandatoryFieldException("price.currency");
		}

		if(dto.getPrice().getAmount() == null){
			throw new MandatoryFieldException("price.amount");
		}

		if(BigDecimal.ZERO.compareTo(dto.getPrice().getAmount()) >= 0){
			throw new IllegalArgumentException("The amount must be greater than 0");
		}

		return service.create(dto);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/product")
	public void update(@RequestBody ProductDTO dto) {
		service.update(dto);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/product/{productCode}")
	public void delete(@PathVariable String productCode) {
		service.delete(productCode);
	}

}
