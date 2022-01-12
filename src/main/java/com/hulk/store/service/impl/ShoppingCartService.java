package com.hulk.store.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulk.store.enumerator.ActionAmountProductEnum;
import com.hulk.store.enumerator.ShoppingCartStatusEnum;
import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.model.ShoppingCartDTO;
import com.hulk.store.persistence.entity.ProductEntity;
import com.hulk.store.persistence.entity.ShoppingCartEntity;
import com.hulk.store.persistence.repository.ShoppingCartRepository;
import com.hulk.store.service.IShoppingCart;

@Service
public class ShoppingCartService implements IShoppingCart {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ProductService productService;

	@Override
	public void createOrUpdate(Long userId, Long total, Long productId) throws ProductException {

		ProductEntity productEntity = productService.getProductEntityById(productId);

		AmountProductDTO amount = new AmountProductDTO(productId, total, ActionAmountProductEnum.DECREASE);
		long totalAllowToBuy = productEntity.getStock() - total < 0 ? productEntity.getStock() : total;

		ProductDTO productDTO = productService.changeAmountProduct(amount);
		if (productDTO == null) {
			return;
		}

		ShoppingCartEntity cartEntity = shoppingCartRepository.findByUserIdAndStatusAndProduct(userId,
				ShoppingCartStatusEnum.INPROGRESS.name(), productEntity);

		if (cartEntity == null) {
			cartEntity = new ShoppingCartEntity();
			cartEntity.setStatus(ShoppingCartStatusEnum.INPROGRESS.name());
			cartEntity.setTotal(totalAllowToBuy);
			cartEntity.setUserId(userId);
			cartEntity.setProduct(productEntity);
		} else {
			cartEntity.setTotal(totalAllowToBuy + cartEntity.getTotal());
		}
		cartEntity = shoppingCartRepository.save(cartEntity);
	}

	@Override
	@Transactional
	public List<ShoppingCartDTO> getAllInProgressProductByUser(Long userId) {
		return shoppingCartRepository.findByUserIdAndStatusOrderById(userId, ShoppingCartStatusEnum.INPROGRESS.name())
				.stream().map(cart -> new ShoppingCartDTO(cart.getUserId(), cart.getTotal(), cart.getProduct().getId(),
						cart.getProduct().getName()))
				.toList();
	}

}
