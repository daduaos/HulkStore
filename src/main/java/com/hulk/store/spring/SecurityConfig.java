package com.hulk.store.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailService userService;

	@Configuration
	@Order(2)
	public static class WebConfigurationAdapter extends WebSecurityConfigurerAdapter {

		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests().antMatchers("/home/**").hasAnyRole("USER", "ADMIN")
					.antMatchers("/cartMVC/**").hasAnyRole("USER", "ADMIN").antMatchers("/productMVC/**")
					.hasRole("ADMIN").antMatchers("/userMVC/**").hasRole("ADMIN").and().formLogin().loginPage("/login")
					.permitAll().defaultSuccessUrl("/home").failureUrl("/login-error").and().logout()
					.logoutUrl("/logout").logoutSuccessUrl("/login");
		}
	}

	@Configuration
	@Order(1)
	public static class ApiConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
		    http 
            .csrf().disable() 
            .authorizeRequests() 
                .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and() 
                .antMatcher("/api/**").httpBasic();
		}
	}

	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
