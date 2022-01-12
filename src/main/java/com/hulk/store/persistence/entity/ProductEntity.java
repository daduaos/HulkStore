package com.hulk.store.persistence.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	private String reference;
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull
	private CategoryEntity categoryId;
	private boolean status;
	private long stock;
	private LocalDateTime dateUpdated;
	private LocalDate dateCreated;



}
