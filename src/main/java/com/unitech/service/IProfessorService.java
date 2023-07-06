package com.unitech.service;

import java.util.List;

import com.unitech.entity.Professor;

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
	 * Ativa o cadastro de um Professor.
	 * 
	 * @param id - identificador do professor que deverá ser ativado
	 * @param codigo - código para ativar o cadastro
	 * @return Entidade Professor com cadastro ativo.
	 */
	public Professor ativarCadastro(long id, String codigo);
	
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
