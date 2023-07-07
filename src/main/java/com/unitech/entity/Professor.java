package com.unitech.entity;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor extends Usuario {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo NOME é obrigatório.")
	@Pattern(regexp = "^[A-Z](.)*", message = "Campo NOME deve iniciar com Letra Maiuscula") 
	private String nome;
	
	@NotBlank(message = "Campo CODIGO é obrigatório.")
	@CPF(message = "CODIGO deve ser um CPF válido.")
	private String codigo;

	private List<Aula> aulas;
	
	public Professor() {
		super();
		aulas = new LinkedList<Aula>();
	}
	
	public Professor(String login,String password ,String nome, String codigo) {
		super(login, password);
		this.nome = nome;
		this.codigo = codigo;
		aulas = new LinkedList<Aula>();
	}

	
}
