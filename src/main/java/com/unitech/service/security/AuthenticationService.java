package com.unitech.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unitech.repository.ProfessorRepository;

/**
 * Esta classe implementa a interface `UserDetailsService` do Spring Security e é usada para carregar detalhes do usuário
 * com base no nome de usuário (login) fornecido durante o processo de autenticação.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 07/07/2023
 */
@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private ProfessorRepository repository;
	
	/**
     * Método usado pelo Spring Security para carregar detalhes do usuário com base no nome de usuário (login) fornecido.
     *
     * @param username O nome de usuário (login) fornecido durante o processo de autenticação.
     * @return Um objeto UserDetails que representa os detalhes do usuário encontrado com base no nome de usuário fornecido.
     * @throws UsernameNotFoundException Se o usuário com o nome de usuário fornecido não for encontrado.
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repository.findByLogin(username);
	}

}