package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, "/api/register").permitAll()
		
		.requestMatchers(HttpMethod.PUT, "/api/books/user/updateUser").hasRole("USER")
		.requestMatchers(HttpMethod.POST, "/api/orders/user/**").hasRole("USER")
		.requestMatchers(HttpMethod.PUT, "/api/user/**").hasRole("USER")
		
		.requestMatchers(HttpMethod.POST, "/api/books/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.PUT, "/api/books/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.DELETE, "/api/books/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET, "/api/orders/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.PUT, "/api/orders/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET, "/api/admin/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
		
		.requestMatchers(HttpMethod.GET, "/api/books/getAllBooks").hasAnyRole("ADMIN","USER")
		.anyRequest().authenticated().and()
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin()
		.and()
		.httpBasic();

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

}
