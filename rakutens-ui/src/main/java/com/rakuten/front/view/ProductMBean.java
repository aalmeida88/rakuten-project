package com.rakuten.front.view;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.rakuten.front.component.ProductService;

@Named
@RequestScoped
public class ProductMBean {

	@Autowired
	private ProductService service;
	
	private LazyProductDataModel products;
	
	@PostConstruct
	public void init(){
		this.products = new LazyProductDataModel(service);
	}

	public LazyProductDataModel getProducts() {
		return products;
	}

	public void setProducts(LazyProductDataModel products) {
		this.products = products;
	}

}
