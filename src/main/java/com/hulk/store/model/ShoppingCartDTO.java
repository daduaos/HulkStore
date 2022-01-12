package com.hulk.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

	private Long userId;
	private Long total;
	private Long productId;
	private String productName;

}
