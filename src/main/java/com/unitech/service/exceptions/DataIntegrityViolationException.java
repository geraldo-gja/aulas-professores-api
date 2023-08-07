package com.unitech.service.exceptions;

/**
 * Exceção personalizada para indicar violação de integridade nos dados.
 * Essa exceção estende a classe RuntimeException, tornando-a uma exceção não verificada.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
public class DataIntegrityViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
     * Construtor da exceção DataIntegrityViolationException.
     *
     * @param nomeClasse     O nome da classe da entidade de dados violados.
     * @param mensagem       A mensagem a ser exibida
     */
	public DataIntegrityViolationException(String nomeClasse, String mensagem) {
        super("ERRO! Houve violação de dados na entidade [" + nomeClasse + "]. \n"
        		+ mensagem);
    }
	
}
