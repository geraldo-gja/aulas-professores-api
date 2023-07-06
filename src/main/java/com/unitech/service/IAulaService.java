package com.unitech.service;

import java.util.List;

import com.unitech.entity.Aula;

/**
 * Interface para serviços de Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
public interface IAulaService {

	/**
	 * Busca uma Aula pelo id. 
	 * Lança ObjectNotFoundException caso não encontre.
	 * 
	 * @param id - identificador de Aula
	 * @return Entidade Aula
	 * @throws ObjectNotFoundException se id não existe
	 */
	public Aula findById (Long id);
	
	/**
	 * Busca todas as aulas.
	 * 
	 * @return Lista de Aula
	 */
	public List<Aula> findAll();
	
	/**
	 * Busca todas as aulas vinculadas a um Professor.
	 * 
	 * @return Lista de Aula
	 */
	public List<Aula> findAllByProfessor(long id);
	
	/**
	 * Salva uma Aula.
	 * O id é gerado de forma sequencial.
	 * Se a data estiver null, será criada com a data atual.
	 * A aula é vinculado a um professor, 
	 * então só será salva se o professor estiver com cadastro ativo.
	 * 
	 * @param aula - Entidade Aula
	 * @param idProfessor - Professor ao qual a aula será vinculada
	 * @return Entidade Aula com ID gerado.
	 */
	public Aula save(Aula aula, long idProfessor);
	
	
	/**
	 * Atualiza uma Aula pelo id.
	 * 
	 * @param aula - Entidade Aula
	 * @return Entidade Aula atualizada
	 */
	public Aula update(Aula aula);
	
	/**
	 * Deleta uma Aula.
	 * 
	 * @param id - identificador de Aula
	 */
	public void delete(Long id);
 
}
