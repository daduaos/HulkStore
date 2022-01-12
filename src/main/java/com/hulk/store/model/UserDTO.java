package com.hulk.store.model;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.hulk.store.enumerator.RoleEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

	private String name;
	private String lastname;
	private String email;
	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	@CreatedDate
	private LocalDate dateCreated;

	public UserDTO(String name, String lastname, String email, @NotNull @NotEmpty String username,
			@NotNull @NotEmpty RoleEnum role, LocalDate dateCreated) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.role = role;
		this.dateCreated = dateCreated;
	}

}
