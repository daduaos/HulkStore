package com.hulk.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hulk.store.enumerator.RoleEnum;
import com.hulk.store.model.UserDTO;
import com.hulk.store.persistence.repository.UserRepository;
import com.hulk.store.service.impl.UserService;

public class UserServiceTest extends SpringIntegrationTestBase {

	@Autowired
	private UserService userService;

	@Autowired
	UserRepository user;

	@Test
	@Order(15)
	public void createUserTest_WhenCreatedNewUser_ThenVerifyThatExistAndThePassword() {

		UserDTO userDTO = new UserDTO("nn", "nn", "nn@nn.com", "nn", RoleEnum.ROLE_USER, LocalDate.now());
		userDTO.setPassword("user");

		userDTO = userService.createUser(userDTO);
		UserDTO actualUserDTO = userService.getUserById(3l);

		assertEquals("NO-PASSWORD", userDTO.getPassword());
		assertNull(actualUserDTO.getPassword());
		assertThat(actualUserDTO).usingRecursiveComparison().ignoringFields("dateCreated", "password")
				.isEqualTo(userDTO);

	}

}
