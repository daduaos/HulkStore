package com.hulk.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControllerMVC {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
        return "redirect:/login";
	}

	@RequestMapping(value = {"/home","/"})
	public String home(Model modelo) {
		return "home";
	}
}
