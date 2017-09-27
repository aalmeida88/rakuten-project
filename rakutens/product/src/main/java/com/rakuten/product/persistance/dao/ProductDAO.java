package com.rakuten.product.persistance.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rakuten.product.persistance.model.Product;

public interface ProductDAO extends MongoRepository<Product, String>, PagingAndSortingRepository<Product, String> {

}