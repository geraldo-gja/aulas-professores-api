package com.unitech.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unitech.entity.Professor;
import com.unitech.repository.ProfessorRepository;
import com.unitech.service.security.TokenService;

/**
 * Esta classe é um filtro de segurança personalizado.
 * O filtro é executado uma vez para cada requisição recebida e é usado para autenticar usuários com base em um token
 * fornecido no cabeçalho da solicitação. 
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 07/07/2023
 */
@Component
public class FilterToken extends OncePerRequestFilter {

	/**
     * Injeção do serviço que lida com operações relacionadas ao token de autenticação.
     */
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ProfessorRepository usuarioRepository;
	
	/**
     * Método que implementa a lógica do filtro de autenticação. É executado para cada requisição recebida.
     * Se um token válido for encontrado no cabeçalho da requisição, o usuário correspondente será autenticado
     * e suas roles serão configuradas no contexto de segurança do Spring.
     *
     * @param request     O objeto HttpServletRequest que representa a requisição HTTP recebida.
     * @param response    O objeto HttpServletResponse que representa a resposta HTTP a ser enviada.
     * @param filterChain O objeto FilterChain usado para continuar a cadeia de filtros após a execução deste filtro.
     * @throws ServletException Se ocorrer uma exceção relacionada ao servlet.
     * @throws IOException      Se ocorrer uma exceção de E/S durante o processamento da requisição.
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		String token = this.tokenService.getToken(request);
		
		if (token != null) {
			String subject = this.tokenService.getSubject(token);
			Professor usuario = this.usuarioRepository.findByLogin(subject);
			
			//authentica usuário e roles
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			//após a autenticação, informa ao Spring que o usuário está logado
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}
