package com.unitech.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitech.entity.Aula;
import com.unitech.repository.AulaRepository;
import com.unitech.service.IAulaService;
import com.unitech.service.exceptions.ObjectNotFoundException;

/**
 * Classe implementação de Serviços de Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@Service
public class AulaService implements IAulaService {

	@Autowired
	private AulaRepository repository;
	
	public Aula findById (Long id) {		
		Optional<Aula> obj = repository.findById(id);	
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Aula.class.getName() ) );
	}
	
	public List<Aula> findAll(){		
		return repository.findAll();
	}
	
	public Aula save(Aula aula){
		aula.setId( generateId() );  
		return repository.save(aula);
	}
	
	public Aula update(Long id, Aula aula){		
		Aula obj = findById(id);
		obj.setTitulo( aula.getTitulo() );
		obj.setDescricao( aula.getDescricao() );
		obj.setDate( aula.getDate() );
		
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		Aula a = findById(id);
		repository.delete(a);
	}
 
	/**
	 * Devolve o próximo ID com base no último criado.
	 * 
	 * @return long
	 */
	private long generateId() {
		long id = 0;
		List<Long> ids = findAll().stream().map(obj -> obj.getId()).collect(Collectors.toList());
		if( ids.size() > 0 ) {
			Collections.sort(ids);
			id = ids.get( ids.size() - 1 ) + 1;			
		}
		return id;
	}
	
}
