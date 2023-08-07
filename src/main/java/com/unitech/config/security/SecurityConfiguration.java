package com.unitech.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração Spring-Security.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 07/07/2023
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true) 
public class SecurityConfiguration {

	@Autowired
	private FilterToken filter;
	
	/**
	 * Informar as rotas que precisam ou não ser atenticadas.
	 * 
	 * @param HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		SecurityFilterChain security;
		
		security = http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeHttpRequests()
				.antMatchers(HttpMethod.POST, "/login")    //libera o login
				.permitAll()
				.antMatchers(HttpMethod.POST, "/professores/save")  //libera cadastro (salvar professores)
				.permitAll()
				.antMatchers(HttpMethod.GET, "/professores/ativar/**")  //libera ativar cadastro
				.permitAll()
			    .anyRequest().authenticated().and()     
	            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
	            .build(); 	
		
		return security;
	}
	

	/**
	 * Gerenciador Spring para injeção de propriedade na classe AuthController
	 * 
	 * @param authenticationConfiguration
	 * @return AuthenticationManager
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager 
		(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
	}
	

	/**
	 * Descriptografar a senha no BD. 
	 * 
	 * @return PasswordEncoder
	 */
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
