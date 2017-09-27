package com.rakuten.product.component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.rakuten.product.api.AmountDTO;
import com.rakuten.product.api.ProductPageDTO;
import com.rakuten.product.exception.AlreadyExistsException;
import com.rakuten.product.exception.NotExistsException;
import com.rakuten.product.api.ProductDTO;
import com.rakuten.product.integration.category.CategoryClient;
import com.rakuten.product.persistance.dao.ProductDAO;
import com.rakuten.product.persistance.mapper.ProductMapper;
import com.rakuten.product.persistance.model.CurrencyCodes;
import com.rakuten.product.persistance.model.Product;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private CategoryClient categoryClient;

	@Override
	public ProductDTO create(ProductDTO dto) {
		validate(dto);

		Product p = productMapper.map(dto, this.getPrice(dto));

		try {
			productDAO.insert(p);
		} catch (DuplicateKeyException e) {
			throw new AlreadyExistsException(dto.getCode());
		}

		return this.productMapper.map(p);
	}

	@Override
	public void delete(String productCode) {
		productDAO.delete(productCode);
	}

	@Override
	public ProductPageDTO list(int page, int pageSize) {
		
		PageRequest p = new PageRequest(page - 1, pageSize, Direction.ASC, "description");
		
		Page<Product> pageResult = productDAO.findAll(p);
		
		List<ProductDTO> products = pageResult.getContent().stream().map(productMapper::map).collect(Collectors.toList());

		ProductPageDTO ret = new ProductPageDTO();
		ret.setProducts(products);
		ret.setTotalProducts(pageResult.getTotalElements());
		
		return ret;
	}

	@Override
	public ProductDTO getByCode(String productCode) {
		Product category = productDAO.findOne(productCode);
		return productMapper.map(category);
	}

	@Override
	public void update(ProductDTO dto) {
		Product c = productMapper.map(dto, this.getPrice(dto));
		productDAO.save(c);
	}

	private void validate(ProductDTO dto) {
		boolean categoryExists = false;
		try {
			categoryExists = categoryClient.categoryExists(dto.getCategoryCode()); 
		} catch (IOException e) {
			throw new RuntimeException("Problems with category service", e);
		}
		if (!categoryExists) {
			throw new NotExistsException("Category." + dto.getCategoryCode());
		}
	}

	private AmountDTO getPrice(ProductDTO dto) {
		AmountDTO price = dto.getPrice();
		if (CurrencyCodes.EUROS.equals(price.getCurrencyCode())) {
			return price;
		}
		AmountDTO newPrice = new AmountDTO();
		newPrice.setCurrencyCode(CurrencyCodes.EUROS);
		newPrice.setAmount(exchangeRateService.convertToEuros(price));
		return newPrice;
	}

}
