package com.unitech.entity;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor extends Usuario {

	private String nome;
	
	private String codigo;
	
	private List<Aula> aulas;
	
	public Professor() {
		super();
	}
	
	public Professor(String login,String password ,String nome, String codigo) {
		super(login, password);
		this.nome = nome;
		this.codigo = codigo;
		this.aulas = new LinkedList<Aula>();
	}
	
}
