package com.unitech.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.unitech.entity.Aula;


/**
 * Interface para métodos de repositório MongoDb da entidade Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface AulaRepository extends MongoRepository<Aula, Long>{
	
	@Query("{'idProfessor': ?0}")
	List<Aula> findAllByProfessor(long idProfessor);

}
