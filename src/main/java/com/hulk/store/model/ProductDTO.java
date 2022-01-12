package com.hulk.store.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	@NotNull
	@NotEmpty
	private String name;
	@NotEmpty
	@NotNull
	private String reference;
	@Positive
	private long categoryId;
	@Null
	private String categoryName;
	@Positive
	private long amount;
	private boolean status;
	private long id;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
