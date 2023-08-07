package com.unitech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//importes para validação e tratamento de erro por request
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

import com.unitech.entity.Professor;
import com.unitech.service.IProfessorService;
import com.unitech.service.exceptions.DataIntegrityViolationException;
import com.unitech.service.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe para requisições Rest relacionado à entidade Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 04/07/2023
 */
@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/professores")
public class ProfessorController {

	@Autowired
	private IProfessorService service;
	
	/**
     * Método exibir logs com informações sobre o método em execução.
     * 
     * @param msg O nome do método a ser registrado no log.
     */
	private void printLog(String msg) {
		log.info("EXECUTANDO METODO [{}] NA CLASSE [{}]", msg, AulaController.class.getSimpleName());
	}
	
	/**
     * Endpoint para buscar um Professor pelo seu ID.
     *
     * @param id O ID do Professor a ser buscado.
     * @return Um ResponseEntity que contém o Professor encontrado se for bem-sucedido ou uma mensagem de erro se não encontrar.
     */
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		printLog("findById");
		try {
			Professor obj = service.findById(id);
			return ResponseEntity.ok(obj);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
	}
	
	/**
     * Endpoint para buscar todos os Professores.
     *
     * @return Um ResponseEntity que contém uma lista de Professores encontrados.
     */
	@GetMapping
	public ResponseEntity<?> findAll(){
		printLog("findAll");
		List<Professor> list = service.findAll();
		return ResponseEntity.ok(list);
	}
	
	/**
     * Endpoint para cadastrar um novo Professor.
     *
     * @param obj O objeto Professor a ser cadastrado. O objeto é validado com base nas anotações de validação.
     * @return Um ResponseEntity que contém uma mensagem de sucesso e um link para ativar o cadastro do Professor
     *         se o cadastro for bem-sucedido. Caso contrário, retorna uma mensagem de erro.
     */
	@PostMapping			
	public ResponseEntity<?> save(@RequestBody @Valid Professor obj) {
		printLog("save");	
		try {
			obj = service.save(obj, true);
			String msg = "Cadastro realizado. Em breve receberá um email para ativação do cadastro. \n" + 
					 "Se preferir pode clicar no link abaixo: \n";
			String link = "http://localhost:8080/professores/ativar/" +
					obj.getId() + "/" + obj.getLogin();
		
			return new ResponseEntity<>(msg+link, HttpStatus.CREATED);
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
	}
	
	/**
     * Endpoint para ativar o cadastro de um Professor com base no seu ID e código de ativação.
     *
     * @param id     O ID do Professor cujo cadastro será ativado.
     * @param codigo O código de ativação fornecido para ativar o cadastro.
     * @return Um ResponseEntity que contém uma mensagem de sucesso com o nome do Professor ativado,
     *         ou uma mensagem de erro se o Professor não for encontrado ou o código for inválido.
     */
	@GetMapping("/ativar/{id}/{codigo}")			
	public ResponseEntity<?> ativar(@PathVariable Long id, @PathVariable String codigo) {
		printLog("ativar");
		try {
			Professor professor = service.ativarCadastro(id, codigo);
			String msg = "Cadastro aprovado! Professor " + professor.getNome() + ", codigo( " + codigo + ").";
			return ResponseEntity.ok(msg);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
	}
	
	/**
     * Endpoint para atualizar as informações de um Professor.
     *
     * @param obj O objeto Professor atualizado a ser salvo. O objeto é validado com base nas anotações de validação.
     * @return Um ResponseEntity que contém o Professor atualizado se a atualização for bem-sucedida,
     *         ou uma mensagem de erro se o Professor não for encontrado.
     */
	@PutMapping    
	public ResponseEntity<?> update(@RequestBody @Valid Professor obj) {
		printLog("update");
		try {
			obj = service.update(obj);
			return ResponseEntity.ok(obj);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
	}

	/**
     * Endpoint para excluir um Professor pelo seu ID.
     *
     * @param id O ID do Professor a ser excluído.
     * @return Um ResponseEntity sem conteúdo (NO_CONTENT) se a exclusão for bem-sucedida,
     *         ou uma mensagem de erro se o Professor não for encontrado ou se houver violação de integridade de dados.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		printLog("delete");
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (ObjectNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
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
