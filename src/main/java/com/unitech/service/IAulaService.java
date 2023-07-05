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
	 * Busca uma aula pelo id. 
	 * Lança ObjectNotFoundException caso não encontre.
	 * 
	 * @param id - identificador de aula
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
	 * Salva uma aula com id gerado de forma sequencial.
	 * 
	 * @param aula - Entidade Aula
	 * @return Entidade Aula com ID gerado.
	 */
	public Aula save(Aula aula);
	
	
	/**
	 * Atualiza uma aula pelo id.
	 * 
	 * @param id - identificador de aula
	 * @param aula - Entidade Aula
	 * @return Entidade Aula atualizada
	 */
	public Aula update(Long id, Aula aula);
	
	/**
	 * Deleta uma aula.
	 * 
	 * @param id - identificador de aula
	 */
	public void delete(Long id);
 
}
