package com.unitech.service.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unitech.entity.Usuario;

/**
 * Classe responsável por gerar e verificar tokens de acesso para autenticação de usuários.
 * Utiliza a biblioteca Java JWT (JSON Web Tokens) para criar e validar tokens.
 * Os tokens são assinados usando o algoritmo HMAC SHA-256 com uma chave secreta
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 07/07/2023
 */
@Service
public class TokenService {

	/**
     * Gera um token de acesso para o usuário fornecido, contendo informações específicas do usuário
     * como reivindicações (claims).
     *
     * @param usuario - O objeto de usuário contendo informações do usuário autenticado.
     * @return Uma string que representa o token de acesso gerado.
     */
	public String gerarToken(Usuario usuario) {
		
		String token = JWT.create()
				.withIssuer("Professores")
				.withClaim("id", usuario.getId())  
				.withClaim("codigo", usuario.getLogin())  
				.withClaim("nome", usuario.getNome())
				.withSubject(usuario.getUsername())
				.withExpiresAt(LocalDateTime.now()  
						.plusMinutes(10)     //vai esperar o token por 10 minutos
						.toInstant(ZoneOffset.of("-03:00")))
				.sign(Algorithm.HMAC256("secreta"));
		
		return token;
	}
	
	/**
     * Obtém o token de acesso de uma solicitação HTTP, removendo o prefixo "Bearer " do cabeçalho de autorização,
     * caso esteja presente.
     *
     * @param request - O objeto HttpServletRequest que representa a requisição HTTP recebida.
     * @return Uma string contendo o token de acesso extraído da requisição HTTP.
     *         Se o token não estiver presente ou não for válido, será retornado null.
     */
	public String getToken(HttpServletRequest request) {
		
		String token = null;
		String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null) 
			token = authorizationHeader.replace("Bearer ", "");
			
		return token;
	}
	
	/**
     * Obtém o "subject" (assunto) do token de acesso fornecido.
     *
     * @param token A string que representa o token de acesso a ser verificado.
     * @return O "subject" (assunto) do token, que geralmente é o nome de usuário do usuário autenticado.
     *         Se o token não for válido ou tiver expirado, será retornado null.
     */
	public String getSubject(String token) {
		
		String subject = JWT.require(Algorithm.HMAC256("secreta"))
				.withIssuer("Professores")
				.build()
				.verify(token) //verifica a expiração
				.getSubject();
		
		return subject;
	}
}
