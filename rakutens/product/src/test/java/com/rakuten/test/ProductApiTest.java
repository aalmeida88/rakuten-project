package com.rakuten.test;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rakuten.TestContext;
import com.rakuten.TestUtils;
import com.rakuten.product.api.AmountDTO;
import com.rakuten.product.api.ProductDTO;
import com.rakuten.product.api.ProductPageDTO;
import com.rakuten.product.controller.ProductController;
import com.rakuten.product.exception.AlreadyExistsException;
import com.rakuten.product.exception.MandatoryFieldException;
import com.rakuten.product.exception.NotExistsException;
import com.rakuten.product.exception.UnsuportedCurrencyException;
import com.rakuten.product.persistance.dao.ProductDAO;
import com.rakuten.product.persistance.model.CurrencyCodes;

import javassist.NotFoundException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class ProductApiTest {
	
	@Autowired
    private ProductController controller;
	
	@Autowired
	private ProductDAO dao;
	
	@Before
	public void init(){
		dao.deleteAll();
	}

    @Test
    public void creates() {
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT);
    	ProductDTO newDTO = controller.create(request);
    	Assert.assertEquals(request.getCode(), newDTO.getCode());
    }
    
    @Test(expected = AlreadyExistsException.class)
    public void controlDuplicates() {
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT);
    	controller.create(request);
    	controller.create(request);
    }
    
    @Test(expected = MandatoryFieldException.class)
    public void mandatoryPrice() {
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, null);
    	controller.create(request);
    }
    
    @Test(expected = MandatoryFieldException.class)
    public void mandatoryPriceCurrency() {
    	AmountDTO price = new AmountDTO();
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, price);
    	controller.create(request);
    }
    
    @Test(expected = MandatoryFieldException.class)
    public void mandatoryPriceAmount() {
    	AmountDTO price = new AmountDTO();
    	price.setCurrencyCode(CurrencyCodes.US_DOLLARS);
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, price);
    	controller.create(request);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void priceGreaterThanZero() {
    	AmountDTO price = new AmountDTO();
    	price.setCurrencyCode(CurrencyCodes.US_DOLLARS);
    	price.setAmount(BigDecimal.ZERO);
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, price);
    	controller.create(request);
    }
    
    @Test(expected = NotExistsException.class)
    public void checkCategory() {
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_NON_EXISTENT);
    	controller.create(request);
    }
    
    @Test(expected = NotExistsException.class)
    public void checkDeleteProduct() {
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT);
    	controller.create(request);
    	ProductDTO ret = controller.get(request.getCode());
    	Assert.assertEquals(request.getCode(), ret.getCode());
    	controller.delete(request.getCode());
	controller.get(request.getCode());
    }
    
    @Test(expected = UnsuportedCurrencyException.class)
    public void currencyNotSupportedException(){
    	AmountDTO price = new AmountDTO();
    	price.setCurrencyCode("CUAK");
    	price.setAmount(BigDecimal.ONE);
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, price);
    	controller.create(request);
    }
    
    @Test
    public void correctConvertionRatio() {
    	BigDecimal amountValue = BigDecimal.valueOf(10);
    	AmountDTO price = new AmountDTO();
    	price.setCurrencyCode(CurrencyCodes.US_DOLLARS);
    	price.setAmount(amountValue);
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT, price);
    	ProductDTO ret = controller.create(request);
    	Assert.assertEquals(ret.getPrice().getCurrencyCode(), CurrencyCodes.EUROS);
    	Assert.assertTrue( ret.getPrice().getAmount().subtract(new BigDecimal(7.69)).compareTo(BigDecimal.ONE) < 0 );
    }
    
    @Test
    public void update(){
    	ProductDTO request = this.buildProductDTO("code", TestUtils.CATEGORY_CODE_EXISTENT);
    	controller.create(request);
    	request.setDescription("newDescription");
    	controller.update(request);
    	Assert.assertEquals(controller.get(request.getCode()).getDescription(), request.getDescription());
    }
    
    @Test
    public void list(){
    	int tot = 20;
    	int pageSize = 5;
    	IntStream.range(0, tot).forEach(i -> {
    		controller.create(this.buildProductDTO("code_"+i, TestUtils.CATEGORY_CODE_EXISTENT));
    	});
    	ProductPageDTO page1 = controller.list(1, pageSize);
    	Assert.assertEquals(page1.getTotalProducts(),tot);
    	Assert.assertEquals(page1.getProducts().size(),pageSize);
    }
    
    
    
    private ProductDTO buildProductDTO(String code, String categoryCode){
    	AmountDTO price = new AmountDTO();
    	price.setAmount(BigDecimal.valueOf(5.0));
    	price.setCurrencyCode(CurrencyCodes.EUROS);
    	return this.buildProductDTO(code, categoryCode, price);
    }
    
    private ProductDTO buildProductDTO(String code, String categoryCode, AmountDTO price){
    	ProductDTO dto = new ProductDTO();
    	dto.setCode(code);
    	dto.setCategoryCode(categoryCode);
    	dto.setPrice(price);
    	return dto;
    }

	
}
