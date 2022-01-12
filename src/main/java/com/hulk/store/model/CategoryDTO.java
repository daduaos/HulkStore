package com.hulk.store.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {

	private Long id;
	@NotNull
	@NotEmpty
	private String name;
}
