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

@Component
public class FilterToken extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ProfessorRepository usuarioRepository;
	
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
