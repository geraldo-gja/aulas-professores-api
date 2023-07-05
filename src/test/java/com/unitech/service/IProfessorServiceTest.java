package com.unitech.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unitech.entity.Professor;
import com.unitech.repository.ProfessorRepository;
import com.unitech.service.exceptions.ObjectNotFoundException;
import com.unitech.service.impl.ProfessorService;

/**
 * Classe de Teste de Serviços de Professor.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class IProfessorServiceTest {

	@Autowired
	private ProfessorRepository repository; //usado apenas no teste06, deletar todos os registros
	
	@Autowired
	private ProfessorService service;
	

	
	@Test
	@DisplayName("Deve salvar um Professor")
	void teste01() {
		
		Professor obj1 = new Professor("geraldo.gja@gmail.com", "123", "Geraldo", "123456");
		obj1 = service.save(obj1);
		Assertions.assertEquals(1, obj1.getId());
		Assertions.assertNotNull(obj1.getAulas());
		
		//set ID para sequencia conforme registros no BD
		Professor obj2 = new Professor("email@gmail.com", "456", "Professor", "987654");
		obj2.setId(0L);
		obj2 = service.save(obj2);
		Assertions.assertEquals(2, obj2.getId());
	}
	
	@Test
	@DisplayName("Deve atualizar um Professor")
	void teste02() {
		
		//ignora ID e Date
		Professor obj1 = new Professor("geraldo.gja@gmail.com", "123", "Geraldo Jorge", "123456");
		obj1 = service.update(1L,obj1);
		Assertions.assertEquals("Geraldo Jorge", obj1.getNome());
		
		//ignora alteração no ID do objeto
		Professor obj2 = new Professor("email@gmail.com", "456", "Professor Teste", "987654");
		obj2.setId(0L);
		obj2 = service.update(2L, obj2);
		Assertions.assertEquals(2, obj2.getId());
	}
	
	@Test
	@DisplayName("Deve buscar um Professor por ID")
	void teste03() {
		Professor obj = service.findById(1L);
		Assertions.assertEquals("geraldo.gja@gmail.com", obj.getLogin() );
		
		//verifica o disparo de exception
		Assertions.assertThrows
			( ObjectNotFoundException.class, () -> service.findById(5L) );
	}
	
	@Test
	@DisplayName("Deve buscar todos os registros de Professor")
	void teste04() {
		List<Professor> lista = service.findAll();
		Assertions.assertEquals(2, lista.size() );
	}
	
	@Test
	@DisplayName("Deve deletar um registro de Professor")
	void teste05() {
		service.delete(1L);
		Assertions.assertThrows
		( ObjectNotFoundException.class, () -> service.findById(1L) );
		
		List<Professor> lista = service.findAll();
		Assertions.assertEquals(1, lista.size() );
	}
	
	@Test
	@DisplayName("Deve deletar todos os registros de Professor")
	void teste06() {
		repository.deleteAll();   
		Assertions.assertEquals(0, service.findAll().size());
	}
}
