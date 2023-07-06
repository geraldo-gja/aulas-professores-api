package com.unitech.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitech.entity.Professor;
import com.unitech.repository.ProfessorRepository;
import com.unitech.service.IProfessorService;
import com.unitech.service.exceptions.DataIntegrityViolationException;
import com.unitech.service.exceptions.ObjectNotFoundException;

/**
 * Classe implementação de Serviços de Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@Service
public class ProfessorService implements IProfessorService {

	@Autowired
	private ProfessorRepository repository;
	
	@Override
	public Professor findById(Long id) {
		Optional<Professor> obj = repository.findById(id);	
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Professor.class.getName() ) );
	}

	@Override
	public List<Professor> findAll() {
		return repository.findAll();
	}

	@Override
	public Professor save(Professor professor) {
		professor.setId( generateId() );  	
		return repository.save(professor);
	}

	@Override
	public Professor update(Professor professor) {
		
		Professor obj = findById(professor.getId());  //confirma no BD se o Objeto existe
		obj.setNome( professor.getNome() );  
		obj.setPassword( professor.getPassword() );
		obj.setAulas( professor.getAulas() );
		
		return repository.save(obj);
	}
	
	@Override
	public Professor ativarCadastro(long id, String codigo) {
		Professor p = findById(id);
		if( codigo.equals(p.getCodigo()) )
			p.setAtivo(true);
		return repository.save(p);
	}

	@Override
	public void delete(Long id) {
		
		Professor obj = findById(id);
		if( obj.getAulas().size() == 0 )
			repository.delete(obj);					
		else
			throw new DataIntegrityViolationException("Professor não pode ser deletado! Possue aulas associadas.");	 
	}
	
	/**
	 * Devolve o próximo ID com base no último criado.
	 * 
	 * @return long
	 */
	private long generateId() {
		long id = 1;
		List<Long> ids = findAll().stream().map(obj -> obj.getId()).collect(Collectors.toList());
		if( ids.size() > 0 ) {
			Collections.sort(ids);
			id = ids.get( ids.size() - 1 ) + 1;			
		}
		return id;
	}
	

}
