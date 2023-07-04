package com.unitech.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitech.entity.Aula;
import com.unitech.repository.AulaRepository;


@Service
public class AulaService {

	@Autowired
	private AulaRepository repository;
	
	public String save(String titulo, String descricao, String dataPrevisao){
		
		try {
			LocalDate data = LocalDate.parse(dataPrevisao);  //"2007-12-03", not null
			//LocalDate data = LocalDate.parse(dataPrevisao, DateTimeFormatter.); 
			
			Aula a1 = new Aula(titulo, descricao, data);
			repository.save(a1);   //save e uptade 
		
		}catch (DateTimeParseException e) {
			return "DateTimeParseException - Erro ao tentar converter a data de previsão";
		}
		
		return "Salvo com sucesso";
	}
	
	public void findAll(String name){
		
		List<Aula> aulas = repository.findAll();
		
		for (int i = 0; i < aulas.size(); i++) {
			System.out.println(aulas.get(i).getTitulo());
		}
	}
}
