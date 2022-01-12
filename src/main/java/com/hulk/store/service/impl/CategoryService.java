package com.hulk.store.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulk.store.model.CategoryDTO;
import com.hulk.store.persistence.entity.CategoryEntity;
import com.hulk.store.persistence.repository.CategoryRepository;
import com.hulk.store.service.ICategory;

@Service
public class CategoryService implements ICategory {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map(category -> new CategoryDTO(category.getId(), category.getName())).toList();
	}

	@Override
	public CategoryDTO getCategoryById(Long id) {
		return categoryRepository.findById(id).map(category -> new CategoryDTO(category.getId(), category.getName())).orElse(null);
	}

	@Override
	public CategoryDTO createCategory(@Valid CategoryDTO category) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(category.getName());
		categoryEntity = categoryRepository.save(categoryEntity);
		category.setId(categoryEntity.getId());
		return category;
	}

}
