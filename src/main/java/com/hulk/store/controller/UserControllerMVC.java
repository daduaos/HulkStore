package com.hulk.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.hulk.store.model.UserDTO;
import com.hulk.store.service.IUser;

@Controller
@RequestMapping("userMVC")
public class UserControllerMVC {
	
	@Autowired
	private IUser userService;

	@PostMapping("/createUser")
	public String createProduct(@Valid @ModelAttribute UserDTO user, Model model, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		userService.createUser(user);
		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping(value = "/new")
	public String newProduct(Model model) {
		model.addAttribute("newUser", new UserDTO());
		return "user";
	}
}
