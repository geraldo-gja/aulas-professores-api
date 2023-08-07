package com.unitech.service.exceptions;

/**
 * Exceção personalizada para indicar que um objeto de uma determinada classe não foi encontrado.
 * Essa exceção estende a classe RuntimeException, tornando-a uma exceção não verificada.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
     * Construtor da exceção ObjectNotFoundException.
     *
     * @param nomeClasse O nome da classe da entidade não encontrada.
     * @param id O ID do objeto que não foi encontrado.
     */
	public ObjectNotFoundException(String nomeClasse, Long id) {
		super("ERRO! A Entidade [" + nomeClasse + "] de ID [" + id + "] não encontrata.");
	}

}
