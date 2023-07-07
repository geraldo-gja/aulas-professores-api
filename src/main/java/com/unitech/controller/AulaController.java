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
import com.unitech.service.security.TokenService;

/**
 * Classe para requisições Rest relacionado à entidadeAula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@RestController
@RequestMapping(value = "/aulas")
public class AulaController {

	@Autowired
	private IAulaService service;
	
	@Autowired
	private IProfessorService professorService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TokenService tokenService;
	
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Aula> findById(@PathVariable Long id) {	
		Aula obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity< List<Aula> > findAll(){
		List<Aula> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/findAllByProfessor")
	public ResponseEntity< List<Aula> > findAllByProfessor(){
		
		String token = tokenService.getToken(request);
		String login = tokenService.getSubject(token);
		Professor p = professorService.findByLogin(login);
		
		List<Aula> list = service.findAllByProfessor(p.getId());
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/save")			
	public ResponseEntity<Aula> save(@RequestBody @Valid Aula aula) {
		aula = service.save(aula);
		return ResponseEntity.ok().body( aula );
	}
	
	@PutMapping("/update/{id}")     
	public ResponseEntity<Aula> update(@RequestBody @Valid Aula aula) {
		aula = service.update(aula);
		return ResponseEntity.ok().body( aula );
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Aula de ID " + id + " deletada.");
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
