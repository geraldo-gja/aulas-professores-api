package com.unitech.entity;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor extends Usuario {

	private static final long serialVersionUID = 1L;

	private List<Aula> aulas;
	
	public Professor() {
		super();
		aulas = new LinkedList<Aula>();
	}
	
	
	public Professor(String login, String password, String nome) {
		super(login, password, nome);
		aulas = new LinkedList<Aula>();
	}

	
}
