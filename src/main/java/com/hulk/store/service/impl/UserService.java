package com.hulk.store.service.impl;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hulk.store.model.UserDTO;
import com.hulk.store.persistence.entity.UserEntity;
import com.hulk.store.persistence.repository.UserRepository;
import com.hulk.store.service.IUser;

@Service
public class UserService implements IUser {

	private static final String NO_PASSWORD = "NO-PASSWORD";
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(user -> new UserDTO(user.getName(), user.getLastname(), user.getEmail(),
				user.getUsername(), user.getRole(), user.getDateCreated())).orElse(null);
	}

	@Override
	public UserDTO createUser(@Valid UserDTO user) {;
		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setLastname(user.getLastname());
		userEntity.setEmail(user.getEmail());
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(encryptPassword(user.getPassword()));
		user.setPassword(NO_PASSWORD);
		userEntity.setRole(user.getRole());
		userEntity.setDateCreated(LocalDate.now());
		userRepository.save(userEntity);
		return user;
	}

	private String encryptPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
