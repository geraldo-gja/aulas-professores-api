package com.unitech.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor extends Usuario {

	private String nome;
	
	private String codigo;
	
	@OneToMany(mappedBy = "aula") 
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
