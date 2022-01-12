package com.hulk.store.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hulk.store.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
