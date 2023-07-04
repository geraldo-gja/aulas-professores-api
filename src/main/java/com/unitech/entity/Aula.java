package com.unitech.entity;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Aula {

	@Id
	private String id;
	
	private String titulo;
	
	private String descricao;
	
	private LocalDate date;
	
	public Aula() {

	}
	
	public Aula(String titulo, String descricao, LocalDate date) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.date = date;
	}
	
	public Aula(String id, String titulo, String descricao, LocalDate date) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.date = date;
	}
	

}
