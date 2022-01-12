package com.hulk.store.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.hulk.store.enumerator.ActionAmountProductEnum;
import com.hulk.store.enumerator.ShoppingCartStatusEnum;
import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.persistence.entity.CategoryEntity;
import com.hulk.store.persistence.entity.ProductEntity;
import com.hulk.store.persistence.entity.ShoppingCartEntity;
import com.hulk.store.persistence.repository.ShoppingCartRepository;
import com.hulk.store.service.impl.ProductService;
import com.hulk.store.service.impl.ShoppingCartService;

public class ShoppingCartTest extends SpringIntegrationTestBase {

	@Autowired
	ShoppingCartService shoppingCartService;
	
	@InjectMocks
	ShoppingCartService shoppingCartServiceMock;
	@Mock
	ProductService productServiceMock;
	@Mock
	ShoppingCartRepository shoppingCartRepositoryMock;


	@Test
	@Order(1)
	public void createOrUpdate_WhenProductIdNotExist_ThenReturnThrowProductException() throws ProductException {

		long userId = 1l;
		long amount = 10l;
		long productId = 1000l;

		ProductException thrown = assertThrows(ProductException.class,
				() -> shoppingCartService.createOrUpdate(userId, amount, productId));

		assertTrue(thrown.getMessage().equals("ProductId 1000 not found"));
	}

	@Test
	@Order(2)
	public void createOrUpdate_WhenProductIdExist_ThenCreateProductInCart() throws ProductException {

		long userId = 1l;
		long amount = 5l;
		long productId = 3;

		
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(3l);
		
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(3l);
		productEntity.setName("Marvel Comic 01");
		productEntity.setReference("CM1");
		productEntity.setCategoryId(categoryEntity);
		productEntity.setStock(6l);
		productEntity.setStatus(true);

		ShoppingCartEntity cartEntityExpected = new ShoppingCartEntity();
		cartEntityExpected.setStatus(ShoppingCartStatusEnum.INPROGRESS.name());
		cartEntityExpected.setTotal(5l);
		cartEntityExpected.setUserId(userId);
		cartEntityExpected.setProduct(productEntity);
		
		AmountProductDTO amountChange = new AmountProductDTO(productId, amount, ActionAmountProductEnum.DECREASE);
		ProductDTO productDTO = new ProductDTO("Marvel Comic 01", "CM1", 3, "comics", 25, true, 3);

		when(productServiceMock.getProductEntityById(productId)).thenReturn(productEntity);
		when(productServiceMock.changeAmountProduct(amountChange)).thenReturn(productDTO);
		
		shoppingCartServiceMock.createOrUpdate(userId, amount, productId);
		
		verify(shoppingCartRepositoryMock, times(1)).save(cartEntityExpected);
	}
	
	@Test
	@Order(3)
	public void createOrUpdate_WhenProductIdExistAndProductExistInCart_ThenUpdateProductInCart() throws ProductException {

		long userId = 1l;
		long amount = 5l;
		long productId = 3;

		
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(3l);
		
		ProductEntity productEntity = new ProductEntity();
		productEntity.setId(3l);
		productEntity.setName("Marvel Comic 01");
		productEntity.setReference("CM1");
		productEntity.setCategoryId(categoryEntity);
		productEntity.setStock(30l);
		productEntity.setStatus(true);

		ShoppingCartEntity cartEntity = new ShoppingCartEntity();
		cartEntity.setStatus(ShoppingCartStatusEnum.INPROGRESS.name());
		cartEntity.setTotal(5l);
		cartEntity.setUserId(userId);
		cartEntity.setProduct(productEntity);
		
		
		AmountProductDTO amountChange = new AmountProductDTO(productId, amount, ActionAmountProductEnum.DECREASE);
		ProductDTO productDTO = new ProductDTO("Marvel Comic 01", "CM1", 3, "comics", 25, true, 3);

		when(productServiceMock.getProductEntityById(productId)).thenReturn(productEntity);
		when(productServiceMock.changeAmountProduct(amountChange)).thenReturn(productDTO);
		when(shoppingCartRepositoryMock.findByUserIdAndStatusAndProduct(anyLong(), any(), any())).thenReturn(cartEntity);
		
		shoppingCartServiceMock.createOrUpdate(userId, amount, productId);
		
		
		ShoppingCartEntity cartEntityExpected = new ShoppingCartEntity();
		cartEntityExpected.setStatus(ShoppingCartStatusEnum.INPROGRESS.name());
		cartEntityExpected.setTotal(10l);
		cartEntityExpected.setUserId(userId);
		cartEntityExpected.setProduct(productEntity);
		
		verify(shoppingCartRepositoryMock, times(1)).save(cartEntityExpected);
	}

}
