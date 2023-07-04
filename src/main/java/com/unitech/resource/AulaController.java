package com.unitech.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unitech.entity.Aula;
import com.unitech.repository.AulaRepository;

@RestController
@RequestMapping(value = "/aulas")
public class AulaController {

	@Autowired
	private AulaRepository repository;
	
	@PostMapping("/add")
	public String saveAula(@RequestBody Aula aula) {
		repository.save(aula);
		return "Aula " + aula.getTitulo() + " adicionada.";
	}
	
	@GetMapping("/findAll")
	public List<Aula> getAulas(){
		return repository.findAll();
	}
	
	@GetMapping("/get/{id}")
	public Optional<Aula> getAula(@PathVariable String id) {
		return repository.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteAula(@PathVariable int id) {
		return "Aula de ID " + id + " deletada.";
	}
}
