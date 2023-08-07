package com.unitech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unitech.entity.Aula;
import com.unitech.entity.Professor;
import com.unitech.service.IAulaService;
import com.unitech.service.IProfessorService;
import com.unitech.service.exceptions.DataIntegrityViolationException;
import com.unitech.service.exceptions.ObjectNotFoundException;
import com.unitech.service.security.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe para requisições Rest relacionado à entidade Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/aula")
public class AulaController {

	@Autowired
	private IAulaService service;
	
	@Autowired
	private IProfessorService professorService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TokenService tokenService;
	
	/**
     * Método exibir logs com informações sobre o método em execução.
     * 
     * @param msg A mensagem a ser registrada no log.
     */
	private void printLog(String msg) {
		log.info("EXECUTANDO METODO [{}] NA CLASSE [{}]", msg, AulaController.class.getSimpleName());
	}
	
	/**
     * Endpoint para buscar uma Aula pelo seu ID.
     *
     * @param id O ID da Aula a ser buscada.
     * @return Um ResponseEntity que contém a Aula encontrada se for bem-sucedido ou uma mensagem de erro se não encontrar.
     */
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {	
		printLog("findById");
		try {
			Aula obj = service.findById(id);
			return ResponseEntity.ok(obj);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
	}
	
	/**
     * Endpoint para buscar todas as Aulas.
     *
     * @return Um ResponseEntity que contém uma lista de Aulas encontradas.
     */
	@GetMapping
	public ResponseEntity< List<?> > findAll(){
		printLog("findAll");
		List<Aula> list = service.findAll();
		return ResponseEntity.ok(list);
	}
	
	/**
     * Endpoint para buscar todas as Aulas associadas a um Professor específico.
     *
     * @return Um ResponseEntity que contém uma lista de Aulas encontradas para o Professor autenticado na requisição.
     */
	@GetMapping("/findAllByProfessor")
	public ResponseEntity<?> findAllByProfessor(){
		printLog("findAllByProfessor");
		String token = tokenService.getToken(request);
		String login = tokenService.getSubject(token);
		Professor p = professorService.findByLogin(login);
		
		List<Aula> list = service.findAllByProfessor(p.getId());
		return ResponseEntity.ok(list);
	}
	
	/**
     * Endpoint para cadastrar uma nova Aula.
     *
     * @param obj O objeto Aula a ser cadastrado. O objeto é validado com base nas anotações de validação.
     * @return Um ResponseEntity que contém a Aula cadastrada se o cadastro for bem-sucedido.
     *         Caso contrário, retorna uma mensagem de erro.
     */
	@PostMapping		
	public ResponseEntity<?> save(@RequestBody @Valid Aula obj) {
		printLog("save");
		try {
			obj = this.service.save(obj);
			return new ResponseEntity<>(obj, HttpStatus.CREATED);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
	}
	
	/**
     * Endpoint para atualizar as informações de uma Aula.
     *
     * @param obj O objeto Aula atualizado a ser salvo. O objeto é validado com base nas anotações de validação.
     * @return Um ResponseEntity que contém a Aula atualizada se a atualização for bem-sucedida,
     *         ou uma mensagem de erro se a Aula não for encontrada.
     */
	@PutMapping  
	public ResponseEntity<?> update(@RequestBody @Valid Aula obj) {
		printLog("update");
		try {
			obj = this.service.update(obj);
			return ResponseEntity.ok(obj);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}    
	}
	
	/**
     * Endpoint para excluir uma Aula pelo seu ID.
     *
     * @param id O ID da Aula a ser excluída.
     * @return Um ResponseEntity sem conteúdo (NO_CONTENT) se a exclusão for bem-sucedida,
     *         ou uma mensagem de erro se a Aula não for encontrada.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		printLog("delete");
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}   
	}
	
	/**
	 * Envia os erros de validation no response Request.
	 * 
	 * @param ex - MethodArgumentNotValidException
	 * @return Map<String, String>
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach( (error) ->{
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		return errors;
	}
	
	
}
