package com.hulk.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hulk.store.enumerator.ActionAmountProductEnum;
import com.hulk.store.exception.ProductException;
import com.hulk.store.model.AmountProductDTO;
import com.hulk.store.model.ProductDTO;
import com.hulk.store.service.impl.ProductService;

@Order(1)
public class ProductServiceTest extends SpringIntegrationTestBase {

	@Autowired
	private ProductService productService;

	@Test
	@Order(1)
	public void getAllProducts_WhenTableNotEmpty_ThenGetAll() throws ProductException {
		List<ProductDTO> productDTOs = productService.getAllProducts();

		List<ProductDTO> productListExpected = getAllProductDTO();

		assertThat(productDTOs).usingRecursiveComparison().isEqualTo(productListExpected);
	}

	@Test
	@Order(2)
	public void getAllActiveProduct_WhenCall_ThenReturnAllActiveProducts() {

		List<ProductDTO> productDTOList = productService.getAllActiveProduct();

		List<ProductDTO> productListExpected = new ArrayList<>();

		ProductDTO dto_1 = new ProductDTO("hulk shirt", "HS1", 1, "shirt", 10, true, 1);
		ProductDTO dto_2 = new ProductDTO("cup DC", "CP1", 2, "cup", 20, true, 2);
		ProductDTO dto_3 = new ProductDTO("Marvel Comic 01", "CM1", 3, "comics", 30, true, 3);
		productListExpected.add(dto_1);
		productListExpected.add(dto_2);
		productListExpected.add(dto_3);

		assertThat(productDTOList).usingRecursiveComparison().isEqualTo(productListExpected);

	}

	@Test
	@Order(3)
	public void getAllProductsByCategory_WhenRequestShirtCat_ThenReturnTwoRecords() {

		List<ProductDTO> actualProductDTOList = productService.getAllProductsByCategory(1);

		List<ProductDTO> productListExpected = new ArrayList<>();
		ProductDTO dto_1 = new ProductDTO("hulk shirt", "HS1", 1, "shirt", 10, true, 1);
		ProductDTO dto_4 = new ProductDTO("Marvel shirt", "MS1", 1, "shirt", 0, false, 4);
		productListExpected.add(dto_1);
		productListExpected.add(dto_4);

		assertThat(actualProductDTOList).usingRecursiveComparison().isEqualTo(productListExpected);
	}

	@Test
	@Order(4)
	public void getAllProductsByCategory_WhenRequestWrongCat_ThenReturnEmptyList() {

		List<ProductDTO> actualProductDTOList = productService.getAllProductsByCategory(10);

		List<ProductDTO> productListExpected = new ArrayList<>();

		assertEquals(productListExpected.size(), actualProductDTOList.size());
	}

	@Test
	@Order(5)
	public void createProduct_WhenCreateNewRecord_ThenReturnIdAndTrueStatus() throws ProductException {
		ProductDTO productDTO = new ProductDTO("toy", "TY10", 4, "toy", 10000, false, 0);

		ProductDTO actualProductDTO = productService.createProduct(productDTO);

		assertTrue(actualProductDTO.getId() > 0);
		assertTrue(actualProductDTO.isStatus());
	}

	@Test
	@Order(6)
	public void createProduct_WhenIsWrongCategory_ThenThrowProductException() throws ProductException {
		ProductDTO productDTO = new ProductDTO("toy", "TY10", 7, "toy", 10000, false, 0);

		ProductException thrown = assertThrows(ProductException.class, () -> productService.createProduct(productDTO),
				"Product could not be created");
		assertTrue(thrown.getMessage().contains("Product could not be created"));
	}

	@Test
	@Order(7)
	public void getProductById_WhenIdExist_ThenReturnRecord() {

		ProductDTO productDTO = productService.getProductById(1);

		ProductDTO expectedProduct = new ProductDTO("hulk shirt", "HS1", 1, "shirt", 10, true, 1);

		assertThat(productDTO).usingRecursiveComparison().isEqualTo(expectedProduct);
	}

	@Test
	@Order(8)
	public void getProductById_WhenIdNotExist_ThenReturnNull() {

		ProductDTO productDTO = productService.getProductById(10l);

		assertNull(productDTO);
	}

	@Test
	@Order(9)
	public void changeAmountProduct_WhenProductIdNotExist_ThenThrowException() {

		AmountProductDTO amountProductDTO = new AmountProductDTO(10, 10, ActionAmountProductEnum.INCREASE);
		ProductException thrown = assertThrows(ProductException.class,
				() -> productService.changeAmountProduct(amountProductDTO));

		assertTrue(thrown.getMessage().equals("ProductId 10 not found"));
	}

	@Test
	@Order(10)
	public void changeAmountProduct_WhenProductIsInactiveAndIncraseStock_ThenIncreaseStockAndChangeToActive()
			throws ProductException {

		AmountProductDTO amountProductDTO = new AmountProductDTO(4, 1000, ActionAmountProductEnum.INCREASE);
		ProductDTO productDTO = productService.changeAmountProduct(amountProductDTO);

		ProductDTO dtoExpected = new ProductDTO("Marvel shirt", "MS1", 1, "shirt", 1000, true, 4);

		assertEquals(dtoExpected, productDTO);

	}

	@Test
	@Order(11)
	public void changeAmountProduct_WhenProductIsActiveAndDecraseStockToZero_ThenChangeToInactive()
			throws ProductException {

		AmountProductDTO amountProductDTO = new AmountProductDTO(3, 50, ActionAmountProductEnum.DECREASE);
		ProductDTO productDTO = productService.changeAmountProduct(amountProductDTO);

		ProductDTO dtoExpected = new ProductDTO("Marvel Comic 01", "CM1", 3, "comics", 0, false, 3);

		assertEquals(dtoExpected, productDTO);

	}

	private List<ProductDTO> getAllProductDTO() {
		List<ProductDTO> productListExpected = new ArrayList<>();

		ProductDTO dto_1 = new ProductDTO("hulk shirt", "HS1", 1, "shirt", 10, true, 1);
		ProductDTO dto_2 = new ProductDTO("cup DC", "CP1", 2, "cup", 20, true, 2);
		ProductDTO dto_3 = new ProductDTO("Marvel Comic 01", "CM1", 3, "comics", 30, true, 3);
		ProductDTO dto_4 = new ProductDTO("Marvel shirt", "MS1", 1, "shirt", 0, false, 4);

		productListExpected.add(dto_1);
		productListExpected.add(dto_2);
		productListExpected.add(dto_3);
		productListExpected.add(dto_4);
		return productListExpected;
	}

}
