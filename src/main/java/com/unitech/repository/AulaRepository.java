package com.unitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unitech.entity.Aula;


public interface AulaRepository extends MongoRepository<Aula, Long>{

}
