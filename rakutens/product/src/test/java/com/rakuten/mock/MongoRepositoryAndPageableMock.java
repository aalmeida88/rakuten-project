package com.rakuten.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class MongoRepositoryAndPageableMock<T> implements MongoRepository<T, String>, PagingAndSortingRepository<T, String> {
	
	private Map<String, T> documents = new HashMap<>();

	
	protected abstract String getId(T t);
	
	
	@Override
	public <S extends T> S save(S entity) {
		documents.put(getId(entity), entity);
		return entity;
	}

	@Override
	public T findOne(String id) {
		return documents.get(id);
	}

	@Override
	public boolean exists(String id) {
		return documents.containsKey(id);
	}

	@Override
	public Iterable<T> findAll(Iterable<String> ids) {
		return StreamSupport.stream(ids.spliterator(), false)
			.filter(Objects::nonNull)
			.map(documents::get)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	@Override
	public long count() {
		return documents.size();
	}

	@Override
	public void delete(String id) {
		documents.remove(id);
	}

	@Override
	public void delete(T entity) {
		this.delete(this.getId(entity));
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		StreamSupport.stream(entities.spliterator(), false).forEach(this::delete);
	}

	@Override
	public void deleteAll() {
		documents.clear();
	}

	@Override
	public <S extends T> S findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends T> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		int size = Math.min(pageable.getPageSize(), documents.size());
		
		List<T> elements = documents.values().stream().limit(size).collect(Collectors.toList());
		
		return new PageMock<>(documents.size(), elements);
	}

	@Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		return StreamSupport.stream(entities.spliterator(), false).map(this::save).collect(Collectors.toList());
	}

	@Override
	public List<T> findAll() {
		return new ArrayList<T>(documents.values());
	}

	@Override
	public List<T> findAll(Sort sort) {
		return this.findAll();
	}

	@Override
	public <S extends T> S insert(S entity) {
		String id = this.getId(entity);
		if(documents.containsKey(id)){
			throw new DuplicateKeyException(id);
		}
		documents.put(id, entity);
		return entity;
	}

	@Override
	public <S extends T> List<S> insert(Iterable<S> entities) {
		return StreamSupport.stream(entities.spliterator(), false).map(this::insert).collect(Collectors.toList());
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

}
