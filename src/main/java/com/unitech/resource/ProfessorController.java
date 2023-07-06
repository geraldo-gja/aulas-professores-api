package com.unitech.resource;

import java.util.List;

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
	
	@PostMapping("/save")			//TODO  @Valid
	public ResponseEntity<Professor> save(@RequestBody Professor professor) {
		professor = service.save(professor);
		return ResponseEntity.ok().body( professor );
	}
	
	@PutMapping("/ativar/{id}/{codigo}")			
	public ResponseEntity<Professor> ativar(@PathVariable Long id, @PathVariable String codigo) {
		Professor professor = service.ativarCadastro(id, codigo);
		return ResponseEntity.ok().body( professor );
	}
	
	@PutMapping("/update/{id}")     //TODO  @Valid
	public ResponseEntity<Professor> update(@RequestBody Professor professor) {
		professor = service.update(professor);
		return ResponseEntity.ok().body( professor );
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Professor de ID " + id + " deletado.";
	}
}
