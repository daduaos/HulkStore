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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.service.IProduct;

@RestController
@RequestMapping("/api/product")

public class ProductController {

	@Autowired
	private IProduct productService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getAllProductsByCategory(id));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product, BindingResult result) throws ProductException {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
	}

	@PutMapping("/amountProduct")
	public ResponseEntity<ProductDTO> amountProduct(@Valid @RequestBody AmountProductDTO increaseProductDTO,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		try {
			return ResponseEntity.ok(productService.changeAmountProduct(increaseProductDTO));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	

}
