package com.unitech.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unitech.entity.Professor;
import com.unitech.repository.ProfessorRepository;
import com.unitech.service.IProfessorService;
import com.unitech.service.email.EmailService;
import com.unitech.service.exceptions.DataIntegrityViolationException;
import com.unitech.service.exceptions.ObjectNotFoundException;


/**
 * Classe implementação de Serviços de Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
@Service
public class ProfessorService implements IProfessorService {

	@Autowired
	private ProfessorRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	/**
     * {@inheritDoc}
     */
	@Override
	public Professor findById(Long id) {
		Optional<Professor> obj = repository.findById(id);	
		return obj.orElseThrow( () -> new ObjectNotFoundException(Professor.class.getSimpleName(), id) );
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public Professor findByLogin(String login) {
		Professor obj = repository.findByLogin(login);	
		return obj;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<Professor> findAll() {
		return repository.findAll();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Professor save(Professor professor, boolean enviarEmail) {
		
		professor.setId( generateId() );  	
		professor.setPassword( encriptografarSenha(professor.getPassword()) );
		
		if( findByLogin(professor.getLogin()) != null )
			throw new DataIntegrityViolationException(Professor.class.getSimpleName(), "Login já existente na base de dados.");	 
	
		professor = repository.save(professor);
		if(enviarEmail)
			enviarEmailConfimacaoCadastro( professor ); 
		
		return professor;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Professor update(Professor professor) {
		
		Professor obj = findById(professor.getId());  //confirma no BD se o Objeto existe
		obj.setNome( professor.getNome() );  
		obj.setPassword( professor.getPassword() );
		obj.setAulas( professor.getAulas() );
		
		return repository.save(obj);
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public Professor ativarCadastro(long id, String login) {
		Professor p = findById(id);
		if( login.equals(p.getLogin()) )
			p.setAtivo(true);
		return repository.save(p);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void delete(Long id) {
		
		Professor obj = findById(id);
		if( obj.getAulas().size() == 0 )
			repository.delete(obj);					
		else
			throw new DataIntegrityViolationException(Professor.class.getSimpleName(), "Não pode ser deletado! Possue aulas associadas.");	 
	}
	
	/**
	 * Encriptografada senha utilizando o proocolo BCrypt.
	 * 
	 * @param senha
	 * @return String - Senha encriptografada
	 */
	private String encriptografarSenha( String senha ) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	
	/**
	 * Devolve o próximo ID com base no último criado.
	 * 
	 * @return long
	 */
	private long generateId() {
		long id = 1;
		List<Long> ids = findAll().stream().map(obj -> obj.getId()).collect(Collectors.toList());
		if( ids.size() > 0 ) {
			Collections.sort(ids);
			id = ids.get( ids.size() - 1 ) + 1;			
		}
		return id;
	}
	
	/**
	 * Envia email para confirmação de cadastro.	
	 */
	private void enviarEmailConfimacaoCadastro(Professor professor) {
		
		String email = professor.getLogin();  //login é um email válido
		String titulo = "Geraldo Jorge - seleção FourD - Confirmação de cadastro";
		String anexo = "arquivos/CV-Geraldo.pdf";
		
		//Conteúdo
		String msgInicial = "Olá ," + professor.getNome() + " <br>" + 
							"Bem-vindo ao sistema da UniTech. <br><br>" + 
							"Click no link abaixo para ativar seu cadastro: <br>";
		String link = "http://localhost:8080/professores/ativar/" +
					   professor.getId() + "/" + professor.getLogin();
		String msgFinal = "<br><br><br> Meu CV segue em anexo. <br><br>";
		String assinatura = "Atenciosamente, <br>" + 
							"Geraldo Jorge <br>" +
							"Full-Stack Developer <br>" + 
							"geraldo.gja@gmail.com <br>" +
							"+55 (88) 9 9999-6947 (whatsapp) <br>";	
		String conteudo = msgInicial + link + msgFinal + assinatura;
		
		emailService.enviarEmailComAnexo(email, titulo,	conteudo, anexo);
	}

}
