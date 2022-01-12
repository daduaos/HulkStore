package com.hulk.store.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hulk.store.persistence.entity.CategoryEntity;
import com.hulk.store.persistence.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByCategoryId(CategoryEntity categoryId);

	List<ProductEntity> findByStatus(boolean status);

	@Modifying
	@Query("UPDATE ProductEntity p SET p.stock = :stock WHERE p.id = :id")
	public int incraseProduct(@Param("stock") long stock, @Param("id") long id);

}
