package com.hulk.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.hulk.store.exception.ProductException;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.model.ShoppingCartDTO;
import com.hulk.store.service.IProduct;
import com.hulk.store.service.IShoppingCart;
import com.hulk.store.spring.CustomUser;

@Controller
@RequestMapping("cartMVC")
public class ShoppingCartMVC {

	@Autowired
	private IProduct productService;

	@Autowired
	private IShoppingCart shoppingCartService;

	@GetMapping
	public String cartMVC(Model model, Authentication auth) {
		CustomUser user = (CustomUser) auth.getPrincipal();
		List<ProductDTO> productDTOs = productService.getAllActiveProduct();
		model.addAttribute("productList", productDTOs);
		model.addAttribute("newCart", new ShoppingCartDTO());
		model.addAttribute("cartList", shoppingCartService.getAllInProgressProductByUser(user.getUserId()));

		return "cart";
	}

	@PostMapping("/add/{idProduct}")
	public String createProduct(@PathVariable Long idProduct, @ModelAttribute ShoppingCartDTO cart, Model model,
			BindingResult result, Authentication auth) throws ProductException {

		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}

		CustomUser user = (CustomUser) auth.getPrincipal();

		shoppingCartService.createOrUpdate(user.getUserId(), cart.getTotal(), idProduct);

		List<ProductDTO> productDTOs = productService.getAllActiveProduct();
		model.addAttribute("productList", productDTOs);
		model.addAttribute("newCart", new ShoppingCartDTO());
		System.out.println(shoppingCartService.getAllInProgressProductByUser(user.getUserId()));
		model.addAttribute("cartList", shoppingCartService.getAllInProgressProductByUser(user.getUserId()));
		return "cart";
	}

}
