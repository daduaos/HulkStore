package com.hulk.store.service;

import java.util.List;

import javax.validation.Valid;

import com.hulk.store.model.CategoryDTO;

public interface ICategory {

	List<CategoryDTO> getAllCategories();

	CategoryDTO getCategoryById(Long id);

	CategoryDTO createCategory(@Valid CategoryDTO category);

}
