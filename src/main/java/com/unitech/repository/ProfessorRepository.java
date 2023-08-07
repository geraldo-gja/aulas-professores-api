package com.unitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.unitech.entity.Professor;


/**
 * Interface para métodos de repositório MongoDb da entidade Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
public interface ProfessorRepository extends MongoRepository<Professor, Long>{

	/**
	 * Busca Professor identificado pelo login.
	 * 
	 * @param login
	 * @return Professor identificado pelo login
	 */
	@Query("{'login': ?0}")
	Professor findByLogin(String login);
}
