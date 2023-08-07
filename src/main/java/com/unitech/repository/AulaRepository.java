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
 * @version 1.0
 * Data: 04/07/2023
 */
public interface AulaRepository extends MongoRepository<Aula, Long>{
	
	/**
	 * Busca todas as aulas ao id do professor.
	 * 
	 * @param idProfessor
	 * @return Lista de aulas vinculadas ao idProfessor
	 */
	@Query("{'idProfessor': ?0}")
	List<Aula> findAllByProfessor(long idProfessor);

}
