package com.hulk.store.spring;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hulk.store.persistence.entity.UserEntity;
import com.hulk.store.persistence.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		System.out.println(userRepository.findAll());
		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> ga = new HashSet<>();
		ga.add(new SimpleGrantedAuthority(userEntity.getRole().toString()));
		return new CustomUser(userEntity.getUsername(), userEntity.getPassword(), ga, userEntity.getId());

	}

}
