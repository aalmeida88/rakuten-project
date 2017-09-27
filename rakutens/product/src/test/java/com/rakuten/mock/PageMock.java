package com.rakuten.mock;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageMock<T> implements Page<T> {
	
	private final int totalElements;
	private final List<T> elements;
	
	public PageMock(int totalElements, List<T> elements) {
		this.totalElements = totalElements;
		this.elements = elements;
	}

	@Override
	public int getNumber() {
		return 1;
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public int getNumberOfElements() {
		return elements.size();
	}

	@Override
	public List<T> getContent() {
		return elements;
	}

	@Override
	public boolean hasContent() {
		return !elements.isEmpty();
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return false;
	}

	@Override
	public boolean isLast() {
		return false;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public Pageable nextPageable() {
		return null;
	}

	@Override
	public Pageable previousPageable() {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}

	@Override
	public int getTotalPages() {
		return 1;
	}

	@Override
	public long getTotalElements() {
		return totalElements;
	}

	@Override
	public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
		return new PageMock<>(totalElements, elements.stream().map(converter::convert).collect(Collectors.toList()));
	}

}
