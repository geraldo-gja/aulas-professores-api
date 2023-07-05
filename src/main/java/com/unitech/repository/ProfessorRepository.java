package com.unitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unitech.entity.Professor;


/**
 * Interface para métodos de repositório MongoDb da entidade Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface ProfessorRepository extends MongoRepository<Professor, Long>{

}
