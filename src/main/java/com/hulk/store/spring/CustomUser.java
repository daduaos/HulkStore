package com.hulk.store.spring;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;

	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long userId) {
		super(username, password, authorities);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
