package com.hulk.store.service;

import java.util.List;

import com.hulk.store.exception.ProductException;
import com.hulk.store.model.ShoppingCartDTO;

public interface IShoppingCart {

	void createOrUpdate(Long userId, Long total, Long idProduct) throws ProductException;

	List<ShoppingCartDTO> getAllInProgressProductByUser(Long userId);
}
