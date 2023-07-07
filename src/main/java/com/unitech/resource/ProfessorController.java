package com.unitech.resource;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

//importes para validação e tratamento de erro por request
import javax.validation.Valid;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unitech.entity.Professor;
import com.unitech.service.IProfessorService;

/**
 * Classe para requisições Rest relacionado à entidade Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@RestController
@RequestMapping(value = "/professores")
public class ProfessorController {

	@Autowired
	private IProfessorService service;
	
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Professor> findById(@PathVariable Long id) {
		Professor obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity< List<Professor> > findAll(){
		List<Professor> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/save")			
	public ResponseEntity<Professor> save(@RequestBody @Valid Professor professor) {
		professor = service.save(professor);
		return ResponseEntity.ok().body( professor );
	}
	
	@PutMapping("/update/{id}")    
	public ResponseEntity<Professor> update(@RequestBody @Valid Professor professor) {
		professor = service.update(professor);
		return ResponseEntity.ok().body( professor );
	}
	
	@PutMapping("/ativar/{id}/{codigo}")			
	public ResponseEntity<String> ativar(@PathVariable Long id, @PathVariable String codigo) {
		Professor professor = service.ativarCadastro(id, codigo);
		return ResponseEntity.ok
				("Cadastro aprovado! Professor " + professor.getNome() + ", codigo( " + codigo + ").");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Professor de ID " + id + " deletado.");
	}
	
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
