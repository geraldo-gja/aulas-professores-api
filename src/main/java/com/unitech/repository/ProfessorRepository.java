package com.unitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.unitech.entity.Professor;


/**
 * Interface para métodos de repositório MongoDb da entidade Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface ProfessorRepository extends MongoRepository<Professor, Long>{

	@Query("{'login': ?0}")
	Professor findByLogin(String login);
}
