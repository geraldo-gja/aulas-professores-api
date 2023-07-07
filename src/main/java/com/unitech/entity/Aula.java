package com.unitech.entity;


import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Aula {

	@Id
	private long id;
	
	@Length(min = 3, max = 100, message = "O campo TITULO deve ter entre {min} e {max} caracteres")
	private String titulo;
	
	private String descricao;
	
	@Future(message = "Campo DATE deve ser data atual ou futura.")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Date date;

	@Min(value = 1, message = "Campo ID_PROFESSOR é obrigatório e deve ser maior que 0.")
	private long idProfessor;
	
	public Aula() {

	}
	
	public Aula(String titulo, String descricao, Date date, long idProfessor) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.date = date;
		this.idProfessor = idProfessor;
	}
	
}
