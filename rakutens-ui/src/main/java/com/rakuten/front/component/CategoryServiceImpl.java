package com.rakuten.front.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rakuten.front.integration.category.CategoryDTO;
import com.rakuten.front.integration.category.CategoryServiceClient;
import com.rakuten.front.view.data.Category;

@Component
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryServiceClient categoryClient;

	@Value("${integration.category.cache.timeMins}")
	private long categoryCacheTimeMins;

	private LoadingCache<String, Category> cache;

	@PostConstruct
	private void init() {
		cache = CacheBuilder.newBuilder().expireAfterAccess(categoryCacheTimeMins, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Category>() {
					@Override
					public Category load(String code) throws Exception {
						LOGGER.info("Loading category with code: {}", code);
						CategoryDTO dto = categoryClient.getCategory(code);
						return getPlainCategory(dto, resolveAncestors(dto));
					}
				});
	}

	@Override
	public Category getCategory(String code) {

		if (code == null) {
			LOGGER.warn("Trying to get category with code null");
			return null;
		}

		try {
			return cache.get(code);
		} catch (ExecutionException e) {
			LOGGER.error("Error loading category with code: " + code, e);
		}
		return null;
	}

	private List<Category> resolveAncestors(CategoryDTO dto) throws IOException {

		// Ancestors cached and not cached...
		Map<String, Category> ancestorsMap = new HashMap<>();
		List<String> ancestorsNotCached = new ArrayList<>();
		dto.getAncestors().stream().forEach(ancestorCode -> {
			Category ancestor = cache.getIfPresent(ancestorCode);
			if (ancestor == null) {
				ancestorsNotCached.add(ancestorCode);
			} else {
				ancestorsMap.put(ancestorCode, ancestor);
			}
		});

		// Map of not cached ancestors from service...
		Map<String, CategoryDTO> ancestorsNotCachedMap = ancestorsNotCached.isEmpty() ? Collections.emptyMap()
				: categoryClient.getCategories(ancestorsNotCached).stream()
						.collect(Collectors.toMap(CategoryDTO::getCode, Function.identity()));

		// use both maps to map from String codes to Categories
		return dto.getAncestors().stream().map(ancestorCode -> {
			return this.getCategoryFromMap(ancestorsMap, ancestorsNotCachedMap, ancestorCode);
		}).collect(Collectors.toList());

	}

	private Category getCategoryFromMap(Map<String, Category> categoryMap, Map<String, CategoryDTO> dtosMap,
			String code) {

		// if have the category in map return
		Category category = categoryMap.get(code);
		if (category != null) {
			return category;
		}

		// get dto from map returned by service
		CategoryDTO dto = dtosMap.get(code);
		if (dto == null) {
			// this should not happen...
			return null;
		}

		// recursive call to fill all ancestors
		List<Category> ancestors = dto.getAncestors().stream().map(ancestorCode -> {
			return getCategoryFromMap(categoryMap, dtosMap, ancestorCode);
		}).collect(Collectors.toList());

		// create category
		category = this.getPlainCategory(dto, ancestors);

		// cache in the map
		categoryMap.put(code, category);
		// update global cache
		cache.put(code, category);

		return category;
	}

	private Category getPlainCategory(CategoryDTO dto, List<Category> ancestors) {
		Category c = new Category();
		c.setCode(dto.getCode());
		c.setDescription(dto.getDescription());
		c.setAncestors(ancestors);
		return c;
	}

}
