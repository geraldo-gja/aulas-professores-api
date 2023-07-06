package com.unitech.service;

import java.util.List;

import com.unitech.entity.Professor;
import com.unitech.service.exceptions.DataIntegrityViolationException;

/**
 * Interface para serviços de Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface IProfessorService {

	/**
	 * Busca um Professor pelo id. 
	 * Lança ObjectNotFoundException caso não encontre.
	 * 
	 * @param id - identificador de Professor
	 * @return Entidade Professor
	 * @throws ObjectNotFoundException se id não existe
	 */
	public Professor findById (Long id);
	
	/**
	 * Busca todos os Professores.
	 * 
	 * @return Lista de Professor
	 */
	public List<Professor> findAll();
	
	/**
	 * Salva um Professor.
	 * O id é gerado de forma sequencial.
	 * 
	 * @param Professor - Entidade Professor
	 * @return Entidade Professor com ID gerado.
	 */
	public Professor save(Professor Professor);
	
	
	/**
	 * Atualiza um Professor pelo id.
	 * 
	 * @param Professor - Entidade Professor
	 * @return Entidade Professor atualizada
	 */
	public Professor update(Professor Professor);
	
	/**
	 * Deleta um Professor.
	 * Se tiver aulas associadas, dispara DataIntegrityViolationException
	 * 
	 * @param id - identificador de Professor
	 */
	public void delete(Long id);
 
}
