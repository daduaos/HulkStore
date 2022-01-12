package com.hulk.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hulk.store.model.CategoryDTO;
import com.hulk.store.service.ICategory;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private ICategory categoryService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
		if (categoryDTOs.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categoryDTOs);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> getProduct(@PathVariable Long id) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(id);
		if (categoryDTO == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categoryDTO);
	}

	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		return ResponseEntity.ok(categoryService.createCategory(category));
	}
	
	/**
	 * disabled category
	 */
}
