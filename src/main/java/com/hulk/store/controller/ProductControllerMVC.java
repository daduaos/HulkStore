package com.hulk.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.hulk.store.enumerator.ActionAmountProductEnum;
import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.service.IProduct;

@Controller
@RequestMapping("/productMVC")
/**
 * missing exceptions
 * 
 * @author pc
 *
 */
public class ProductControllerMVC {

	@Autowired
	private IProduct productService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllProducts(Model model) {
		List<ProductDTO> productDTOs = productService.getAllProducts();
		model.addAttribute("productList", productDTOs);
		return "product";
	}

//	@GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long id) {
//		return ResponseEntity.ok(productService.getAllProductsByCategory(id));
//	}
//
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProduct(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		AmountProductDTO amountProduct = new AmountProductDTO();
		amountProduct.setAction(ActionAmountProductEnum.INCREASE);
		amountProduct.setAmount(1l);
		model.addAttribute("amountProduct", amountProduct);
		return "product";
	}

	@GetMapping(value = "/new")
	public String newProduct(Model model) {
		model.addAttribute("newProduct", new ProductDTO());
		return "product";
	}

	@PostMapping("/createProduct")
	public String createProduct(@Valid @ModelAttribute ProductDTO product, Model model,
			BindingResult result) throws ProductException {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		AmountProductDTO amountProduct = new AmountProductDTO();
		amountProduct.setAction(ActionAmountProductEnum.INCREASE);
		amountProduct.setAmount(1l);
		model.addAttribute("amountProduct", amountProduct);
		ProductDTO dto = productService.createProduct(product);
		model.addAttribute("product", dto);
		return "product";
	}

	@PostMapping("/amountProduct")
	public String amountProduct(@Valid @ModelAttribute(value = "amountProduct") AmountProductDTO amountProduct, Model model,
			BindingResult result) {

		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getAllErrors().toString());
		}
		try {
			ProductDTO dto = productService.changeAmountProduct(amountProduct);
			model.addAttribute("product", dto);
			return "product";
		} catch (Exception e) {
			return "product";
		}
	}

	/**
	 * disabled product
	 */

}
