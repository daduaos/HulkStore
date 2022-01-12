package com.hulk.store.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hulk.store.persistence.entity.ProductEntity;
import com.hulk.store.persistence.entity.ShoppingCartEntity;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
	
	List<ShoppingCartEntity> findByUserIdAndStatusOrderById(Long userId, String status);

	ShoppingCartEntity findByUserIdAndStatusAndProduct(Long userId, String status, ProductEntity product);

}
