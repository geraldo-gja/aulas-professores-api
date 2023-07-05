package com.unitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unitech.entity.Aula;


/**
 * Interface para métodos de repositório MongoDb da entidade Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface AulaRepository extends MongoRepository<Aula, Long>{

}
