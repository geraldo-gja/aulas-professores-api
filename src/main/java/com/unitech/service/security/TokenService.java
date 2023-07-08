package com.unitech.service.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unitech.entity.Usuario;

@Service
public class TokenService {

	public String gerarToken(Usuario usuario) {
		
		String token = JWT.create()
				.withIssuer("Professores")
				.withClaim("id", usuario.getId())  //TODO - O payload do JWT deve possuir o código e nome do professor
				.withClaim("codigo", usuario.getLogin())  
				.withClaim("nome", usuario.getNome())
				.withSubject(usuario.getUsername())
				.withExpiresAt(LocalDateTime.now()  
						.plusMinutes(10)     //vai esperar o token por 10 minutos
						.toInstant(ZoneOffset.of("-03:00")))
				.sign(Algorithm.HMAC256("secreta"));
		
		return token;
	}
	
	public String getToken(HttpServletRequest request) {
		
		String token = null;
		String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null) 
			token = authorizationHeader.replace("Bearer ", "");
			
		return token;
	}
	
	public String getSubject(String token) {
		
		String subject = JWT.require(Algorithm.HMAC256("secreta"))
				.withIssuer("Professores")
				.build()
				.verify(token) //verifica a expiração
				.getSubject();
		
		return subject;
	}
}
