package com.hulk.store.service;

import java.util.List;

import javax.validation.Valid;

import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.persistence.entity.ProductEntity;

public interface IProduct {

	List<ProductDTO> getAllProducts();
	List<ProductDTO> getAllActiveProduct();
	List<ProductDTO> getAllProductsByCategory(long id);
	ProductDTO getProductById(long id);
	ProductDTO createProduct(ProductDTO product) throws ProductException;
	ProductDTO changeAmountProduct(@Valid AmountProductDTO increaseProductDTO) throws ProductException;
	ProductEntity getProductEntityById(long id) throws ProductException;
}
