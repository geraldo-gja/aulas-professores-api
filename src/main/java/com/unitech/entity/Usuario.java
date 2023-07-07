package com.unitech.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public abstract class Usuario {


	@Id
	private Long id;
	
	@Email(message = "LOGIN deve ser um e-mail válido.")
	@Pattern(regexp = "(.)*.com(.)*", message = "E-mail válido deve conter '.com'") 
	private String login; 
	
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$", 
			 message = "PASSWORD deve conter ao menos 8 caractere, um dígito, uma letra maiúscula, uma letra minúscula e um caractere especial. ")
	@Length(min = 8, max = 30, message = "O campo NOME deve ter entre {min} e {max} caracteres")
	private String password;
	
	private boolean isAtivo;
	
	public Usuario() {

	}
	
	public Usuario(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
}
