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

import com.unitech.entity.Aula;
import com.unitech.service.IAulaService;

@RestController
@RequestMapping(value = "/aulas")
public class AulaController {

	@Autowired
	private IAulaService service;
	
	
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
	
	@PostMapping("/save")			//TODO  @Valid
	public ResponseEntity<Aula> save(@RequestBody Aula aula) {
		aula = service.save(aula);
		return ResponseEntity.ok().body( aula );
	}
	
	@PutMapping("/update/{id}")     //TODO  @Valid
	public ResponseEntity<Aula> update(@PathVariable Long id, @RequestBody Aula aula) {
		aula = service.update(id, aula);
		return ResponseEntity.ok().body( aula );
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Aula de ID " + id + " deletada.";
	}
}
