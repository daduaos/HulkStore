package com.hulk.store.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.hulk.store.enumerator.ActionAmountProductEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountProductDTO {
	@Positive
	private long id;
	@Positive
	private long amount;
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private ActionAmountProductEnum action;

}
