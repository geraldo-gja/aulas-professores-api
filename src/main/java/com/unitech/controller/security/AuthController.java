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

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public String login(@RequestBody Login login) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

		//faz autenticação
		Authentication authentication = this.authenticationManager
				.authenticate(usernamePasswordAuthenticationToken);
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		String s = tokenService.gerarToken(usuario);
		return s;
	}
}
