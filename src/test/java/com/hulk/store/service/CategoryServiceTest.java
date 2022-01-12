package com.hulk.store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hulk.store.exception.ProductException;
import com.hulk.store.model.CategoryDTO;
import com.hulk.store.service.impl.CategoryService;

public class CategoryServiceTest extends SpringIntegrationTestBase {

	@Autowired
	private CategoryService categoryService;

	@Test
	@Order(1)
	public void getAllCategories_WhenTableNotEmpty_ThenGetAll() throws ProductException {
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

		List<CategoryDTO> categoryListExpected = getAllCategories();

		assertThat(categoryDTOs).usingRecursiveComparison().isEqualTo(categoryListExpected);
	}

	@Test
	@Order(2)
	public void getCategoryId_WhenIdExist_ThenReturnRecord() {

		CategoryDTO categoryDTO = categoryService.getCategoryById(1l);

		CategoryDTO dto_1 = new CategoryDTO(1L, "shirt");

		assertThat(categoryDTO).usingRecursiveComparison().isEqualTo(dto_1);
	}
	
	@Test
	@Order(3)
	public void createCategory_WhenCreatedNewCategory_ThenVerifyThatExist() {

		CategoryDTO categoryDTO = new CategoryDTO(5L, "electronics");

		CategoryDTO actualCategory = categoryService.createCategory(categoryDTO);

		assertThat(actualCategory).usingRecursiveComparison().isEqualTo(categoryDTO);

	}

	private List<CategoryDTO> getAllCategories() {
		List<CategoryDTO> categoryDTOs = new ArrayList<>();

		CategoryDTO dto_1 = new CategoryDTO(1L, "shirt");
		CategoryDTO dto_2 = new CategoryDTO(2L, "cup");
		CategoryDTO dto_3 = new CategoryDTO(3L, "comics");
		CategoryDTO dto_4 = new CategoryDTO(4L, "toy");

		categoryDTOs.add(dto_1);
		categoryDTOs.add(dto_2);
		categoryDTOs.add(dto_3);
		categoryDTOs.add(dto_4);
		return categoryDTOs;
	}


}
