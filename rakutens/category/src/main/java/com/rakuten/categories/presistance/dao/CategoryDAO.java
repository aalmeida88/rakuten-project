package com.rakuten.categories.presistance.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rakuten.categories.presistance.model.Category;

public interface CategoryDAO extends MongoRepository<Category, String> {
	
	@ExistsQuery("{ 'ancestors' : ?0 }")
	boolean existsByAnsestors(String ancestorCode);
	
	List<Category> findByAncestors(String ancestorCode);
	
	@Query("{ 'code' : { '$in' : ?0 }}")
	List<Category> findByCodes(List<String> codes);
	
	

}