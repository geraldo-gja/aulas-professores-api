package com.unitech.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor extends Usuario {

	private String nome;
	
	private String codigo;
	
	private List<Aula> aulas;
	
}
