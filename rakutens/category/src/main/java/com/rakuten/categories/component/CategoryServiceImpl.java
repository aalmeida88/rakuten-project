package com.rakuten.categories.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.rakuten.categories.api.CategoryCreateDTO;
import com.rakuten.categories.api.CategoryDTO;
import com.rakuten.categories.api.exception.AlreadyExistsException;
import com.rakuten.categories.api.exception.CategoryWithChildrenException;
import com.rakuten.categories.api.exception.NotExistsException;
import com.rakuten.categories.presistance.dao.CategoryDAO;
import com.rakuten.categories.presistance.model.Category;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO dao;

	@Autowired
	private CategoryMapper mapper;

	@Override
	public CategoryDTO create(CategoryCreateDTO dto) {

		List<String> ancestors = this.buildAncestors(dto.getParentCode());

		Category c = new Category();
		c.setCode(dto.getCode());
		c.setDescription(dto.getDescription());
		c.setAncestors(ancestors);

		try {
			dao.insert(c);
		} catch (DuplicateKeyException e) {
			throw new AlreadyExistsException(dto.getCode());
		}

		return mapper.map(c);
	}

	@Override
	public void delete(String categoryCode) {
		// check children
		if (dao.existsByAnsestors(categoryCode)) {
			throw new CategoryWithChildrenException(categoryCode);
		}
		dao.delete(categoryCode);
	}

	@Override
	public boolean existCategory(String categoryCode) {
		return dao.exists(categoryCode);
	}

	@Override
	public List<CategoryDTO> getByCode(List<String> categoryCodes) {
		return dao.findByCodes(categoryCodes).stream().map(mapper::map).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO update(CategoryCreateDTO dto) {

		Category c = this.load(dto.getCode());

		String previousParentCode = c.getParentCode();
		String actualParentCode = dto.getParentCode();
		if ((previousParentCode == null && actualParentCode != null)
				|| (previousParentCode != null && !previousParentCode.equals(actualParentCode))) {
			// move hierarchy
			List<String> ancestors = this.buildAncestors(actualParentCode);
			c.setAncestors(ancestors);
			this.updateChildrenHierarchy(c);
		}

		c.setDescription(dto.getDescription());

		dao.save(c);

		return mapper.map(c);
	}

	// This could be done async with @Async in an other bean
	private void updateChildrenHierarchy(Category c) {
		List<Category> children = dao.findByAncestors(c.getCode());
		children.stream().forEach(child -> {
			int indexOfCategory = child.getAncestors().indexOf(c.getCode());
			List<String> ancestors = new ArrayList<>();
			// previous ancestors
			ancestors.addAll(child.getAncestors().subList(0, indexOfCategory + 1));
			// new parent ancestors
			ancestors.addAll(c.getAncestors());
			child.setAncestors(ancestors);
			dao.save(child);
		});
	}

	private List<String> buildAncestors(String parentCode) {
		List<String> ancestors = Collections.emptyList();
		if (parentCode != null) {
			Category parent = this.load(parentCode);
			// ansestors are my parent ansestors plus my parent code
			ancestors = new ArrayList<>(parent.getAncestors());
			ancestors.add(0, parent.getCode());
		}
		return ancestors;
	}

	private Category load(String code) {
		Category c = dao.findOne(code);
		if (c == null) {
			throw new NotExistsException(code);
		}
		return c;
	}

}
