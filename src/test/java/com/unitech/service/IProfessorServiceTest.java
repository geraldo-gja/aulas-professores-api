package com.unitech.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unitech.entity.Aula;
import com.unitech.entity.Professor;
import com.unitech.repository.ProfessorRepository;
import com.unitech.service.exceptions.DataIntegrityViolationException;
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
	@DisplayName("Deve deletar todos os registros de Professor")
	void teste00() {
		repository.deleteAll();   
		Assertions.assertEquals(0, service.findAll().size());
	}

	@Test
	@DisplayName("Deve salvar um Professor")
	void teste01() {
		
		Professor obj1 = new Professor("geraldo.gja@gmail.com", "321", "Geraldo");
		obj1.setAtivo(true);
		obj1 = service.save(obj1, false);
		Assertions.assertEquals(1, obj1.getId());
		
		Professor obj2 = new Professor("email@gmail.com", "456", "Professor");
		obj2.setAtivo(true);
		obj2 = service.save(obj2, false);
		Assertions.assertNotNull(obj1.getAulas());
		
		Professor obj3 = new Professor("teste1@teste1.com", "456", "Teste 1");
		obj3 = service.save(obj3, false);
		Assertions.assertEquals(3, obj3.getId());
		
		//set ID para sequencia conforme registros no BD
		Professor obj4 = new Professor("teste2@teste2.com", "456", "Teste 2");
		obj4.setId(0L);
		obj4 = service.save(obj4, false);
		Assertions.assertEquals(4, obj4.getId());
		
		Professor obj5= new Professor("geraldo.gja@gmail.com", "321", "Geraldo");
		Assertions.assertThrows
			( DataIntegrityViolationException.class, () -> service.save(obj5, false) );
	}
	
	@Test
	@DisplayName("Deve atualizar um Professor")
	void teste02() {
		Professor obj1 = new Professor("geraldo.gja@gmail.com", "123", "Geraldo Jorge");
		obj1.setId(1L);
		obj1 = service.update(obj1);
		Assertions.assertEquals("Geraldo Jorge", obj1.getNome());
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
	@DisplayName("Deve buscar um Professor por login")
	void teste04() {
		Professor obj = service.findByLogin("geraldo.gja@gmail.com");
		Assertions.assertEquals("geraldo.gja@gmail.com", obj.getLogin() );
		
		Professor obj2 = service.findByLogin("nao@existe.com");
		Assertions.assertNull(obj2);
	}
	
	@Test
	@DisplayName("Deve buscar todos os registros de Professor")
	void teste05() {
		List<Professor> lista = service.findAll();
		Assertions.assertEquals(4, lista.size() );
	}
	
	@Test
	@DisplayName("Deve deletar um registro de Professor")
	void teste06() {
		service.delete(4L);
		List<Professor> lista = service.findAll();
		Assertions.assertEquals(3, lista.size() );
		
		//dispara excetion caso tente deletar um objeto que nao exista
		Assertions.assertThrows
		( ObjectNotFoundException.class, () -> service.findById(4L) ); 
		
		//dispara excetion caso tente deletar um professor que possue aulas assossiadas
		Professor p = service.findById(1L);
		p.getAulas().add(new Aula());
		service.update(p);
		Assertions.assertThrows
			( DataIntegrityViolationException.class, () -> service.delete(1L) ); 
	}
	
	
}
