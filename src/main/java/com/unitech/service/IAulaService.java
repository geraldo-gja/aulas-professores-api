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
	 * Salva uma Aula.
	 * O id é gerado de forma sequencial.
	 * Se a data estiver null, será criada com a data atual.
	 * 
	 * @param aula - Entidade Aula
	 * @return Entidade Aula com ID gerado.
	 */
	public Aula save(Aula aula);
	
	
	/**
	 * Atualiza uma Aula pelo id.
	 * 
	 * @param id - identificador de Aula
	 * @param aula - Entidade Aula
	 * @return Entidade Aula atualizada
	 */
	public Aula update(Long id, Aula aula);
	
	/**
	 * Deleta uma Aula.
	 * 
	 * @param id - identificador de Aula
	 */
	public void delete(Long id);
 
}
