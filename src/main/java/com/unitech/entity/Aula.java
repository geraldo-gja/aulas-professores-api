package com.unitech.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Aula {

	@Id
	private long id;
	
	private String titulo;
	
	private String descricao;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Date date;
	
	private long idProfessor;
	
	public Aula() {

	}
	
	public Aula(String titulo, String descricao, Date date) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.date = date;
	}
	
}
