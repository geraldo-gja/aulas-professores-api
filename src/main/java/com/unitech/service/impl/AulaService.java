package com.unitech.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitech.entity.Aula;
import com.unitech.entity.Professor;
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
	
	@Autowired
	private ProfessorService professorService;
	
	@Override
	public Aula findById (Long id) {		
		Optional<Aula> obj = repository.findById(id);	
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Aula.class.getName() ) );
	}
	
	@Override
	public List<Aula> findAll(){
		return repository.findAll();
	}
	
	public List<Aula> findAllByProfessor(long id){
		List<Aula> l = repository.findAllByProfessor(id);
		return l;
	}
	
	@Override
	public Aula save(Aula aula, long idProfessor){
		aula.setId( generateId() );  
		if( aula.getDate() == null )
			aula.setDate( new Date() );
		
		Professor p = professorService.findById(idProfessor);
		
		aula.setIdProfessor(p.getId());
		aula = repository.save(aula);
		
		atualizarAulasProfessor(aula.getIdProfessor());  
		
		return aula;
	}
	
	@Override
	public Aula update(Aula aula){		
		Aula obj = findById(aula.getId());
		obj.setTitulo( aula.getTitulo() );
		obj.setDescricao( aula.getDescricao() );
		if( aula.getDate() != null )
			obj.setDate( aula.getDate() );
		
		obj =  repository.save(obj);
		
		atualizarAulasProfessor(obj.getIdProfessor());  
		
		return obj;
	}
	
	@Override
	public void delete(Long id) {
		Aula obj = findById(id);
		repository.delete(obj);
		
		atualizarAulasProfessor(obj.getIdProfessor());  
	}

	/**
	 * Atualiza a lista de aulas do professor.
	 * Deve chamar esse método sempre que modificar o documento de aulas.
	 * 
	 * @param idProfessor - long
	 */
	private void atualizarAulasProfessor(long idProfessor) {
		Professor p = professorService.findById(idProfessor);
		p.setAulas( findAllByProfessor(p.getId()) );
		professorService.update(p);
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
