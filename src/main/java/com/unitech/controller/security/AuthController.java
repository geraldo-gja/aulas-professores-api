package com.unitech.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unitech.dto.Login;
import com.unitech.entity.Usuario;
import com.unitech.service.security.TokenService;

import lombok.extern.slf4j.Slf4j;

/** 
 * Classe para requisições Rest relacionado à autenticação e login.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 07/07/2023
 */
@Slf4j
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	/**
     * Método exibir logs com informações sobre o método em execução.
     */
	private void printLog(String msg) {
		log.info("EXECUTANDO METODO [{}] NA CLASSE [{}]", msg, AuthController.class.getSimpleName());
	}
	
	/**
     * Endpoint responsável por autenticar um usuário com base nas credenciais fornecidas 
     * (login e senha) e gerar um token de acesso válido para o usuário autenticado.
     *
     * @param login As informações de login do usuário fornecidas no corpo da solicitação.
     * @return Uma string que representa o token de acesso gerado para o usuário autenticado.
     *         Em caso de sucesso na autenticação, o token será retornado; caso contrário, uma exceção será lançada.
     */
	@PostMapping("/login")
	public String login(@RequestBody Login login) {
		printLog("login");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

		Authentication authentication = this.authenticationManager
				.authenticate(usernamePasswordAuthenticationToken);
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		String s = tokenService.gerarToken(usuario);
		return s;
	}
}
