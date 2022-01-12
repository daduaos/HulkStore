package com.hulk.store.service;

import javax.validation.Valid;

import com.hulk.store.model.UserDTO;

public interface IUser {

	UserDTO getUserById(Long id);

	UserDTO createUser(@Valid UserDTO user);

}
