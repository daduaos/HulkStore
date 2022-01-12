package com.hulk.store.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;

import com.hulk.store.enumerator.ActionAmountProductEnum;
import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.persistence.entity.CategoryEntity;
import com.hulk.store.persistence.entity.ProductEntity;
import com.hulk.store.persistence.repository.ProductRepository;
import com.hulk.store.service.IProduct;

@Service
public class ProductService implements IProduct {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream()
				.map(product -> new ProductDTO(product.getName(), product.getReference(),
						product.getCategoryId().getId(), product.getCategoryId().getName(), product.getStock(),
						product.isStatus(), product.getId()))
				.toList();
	}

	@Override
	public ProductDTO createProduct(ProductDTO product) throws ProductException {

		try {
			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setId(product.getCategoryId());

			ProductEntity productEntity = new ProductEntity();
			productEntity.setStatus(true);
			productEntity.setName(product.getName());
			productEntity.setReference(product.getReference());
			productEntity.setCategoryId(categoryEntity);
			productEntity.setStock(product.getAmount());
			productEntity.setDateUpdated(LocalDateTime.now());
			productEntity.setDateCreated(LocalDate.now());
			productEntity = productRepository.save(productEntity);
			product.setStatus(true);
			product.setId(productEntity.getId());
			return product;
		} catch (Exception e) {
			throw new ProductException("Product could not be created");
		}
	}

	@Override
	public List<ProductDTO> getAllProductsByCategory(long id) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(id);
		return productRepository.findByCategoryId(categoryEntity).stream()
				.map(product -> new ProductDTO(product.getName(), product.getReference(),
						product.getCategoryId().getId(), product.getCategoryId().getName(), product.getStock(),
						product.isStatus(), product.getId()))
				.toList();
	}

	@Override
	public ProductDTO getProductById(long id) {
		return productRepository.findById(id)
				.map(product -> new ProductDTO(product.getName(), product.getReference(),
						product.getCategoryId().getId(), product.getCategoryId().getName(), product.getStock(),
						product.isStatus(), product.getId()))
				.orElse(null);
	}

	@Override
	public ProductEntity getProductEntityById(long id) throws ProductException {
		return productRepository.findById(id).orElseThrow(() -> new ProductException(String.format("ProductId %s not found",id)));
	}

	@Override
	@Transactional
	public ProductDTO changeAmountProduct(@Valid AmountProductDTO increaseProductDTO) throws ProductException {
		ProductDTO productDTO = null;
		Optional<ProductEntity> proOptional = productRepository.findById(increaseProductDTO.getId());
		if (!proOptional.isPresent()) {
			throw new ProductException(String.format("ProductId %s not found", increaseProductDTO.getId()));
		}
		try {
			ProductEntity productEntity = proOptional.get();
			long total;
			if (increaseProductDTO.getAction().equals(ActionAmountProductEnum.INCREASE)) {
				total = productEntity.getStock() + increaseProductDTO.getAmount();
				productEntity.setStock(total);
			} else {
				total = productEntity.getStock() - increaseProductDTO.getAmount();
				productEntity.setStock(total < 0 ? 0 : total);
			}
			productEntity.setStatus(total > 0);
			ProductEntity productEntitySaved = productRepository.save(productEntity);
			productDTO = new ProductDTO(productEntitySaved.getName(), productEntitySaved.getReference(),
					productEntitySaved.getCategoryId().getId(), productEntitySaved.getCategoryId().getName(),
					productEntitySaved.getStock(), productEntitySaved.isStatus(), productEntitySaved.getId());
		} catch (DeadlockLoserDataAccessException e) {
			changeAmountProduct(increaseProductDTO);
		} catch (Exception e) {
			throw new ProductException();
		}
		return productDTO;
	}

	@Override
	public List<ProductDTO> getAllActiveProduct() {
		return productRepository.findByStatus(true).stream()
				.map(product -> new ProductDTO(product.getName(), product.getReference(),
						product.getCategoryId().getId(), product.getCategoryId().getName(), product.getStock(),
						product.isStatus(), product.getId()))
				.toList();
	}

}
