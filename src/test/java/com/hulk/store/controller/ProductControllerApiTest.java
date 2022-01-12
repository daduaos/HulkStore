package com.hulk.store.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.service.SpringIntegrationTestBase;

public class ProductControllerApiTest extends SpringIntegrationTestBase {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void shouldReturnListProduct() throws Exception {
		mvc.perform(get("/api/product/all").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("hulk shirt")))
        .andExpect(jsonPath("$[1].name", is("cup DC")))
        .andExpect(jsonPath("$[2].name", is("Marvel Comic 01")))
        .andExpect(jsonPath("$[3].name", is("Marvel shirt")));
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void shouldReturnCategory() throws Exception {
		mvc.perform(get("/api/product/category/1").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("hulk shirt")))
        .andExpect(jsonPath("$[0].reference", is("HS1")))
        .andExpect(jsonPath("$[0].categoryId", is(1)))
        .andExpect(jsonPath("$[0].categoryName", is("shirt")))
        .andExpect(jsonPath("$[0].amount", is(10)));
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void shouldCreateProduct() throws Exception {
		
		ProductDTO dto = new ProductDTO("newProduct", "NP1", 4, null, 500L, false, 0);

		Gson gson = new Gson();
		
		mvc.perform(post("/api/product/new").contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(dto)))
		.andDo(print())
		.andExpect(status().isCreated());
	}
}
