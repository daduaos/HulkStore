package com.hulk.store.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hulk.store.enumerator.RoleEnum;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastname;
	private String email;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	private LocalDate dateCreated;

}
