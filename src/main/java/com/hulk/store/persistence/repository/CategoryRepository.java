package com.hulk.store.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hulk.store.persistence.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
