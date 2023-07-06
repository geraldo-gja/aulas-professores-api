package com.unitech.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public abstract class Usuario {


	@Id
	private Long id;
	
	private String login; //email
	
	private String password;
	
	private boolean isAtivo;
	
	public Usuario() {

	}
	
	public Usuario(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
}
