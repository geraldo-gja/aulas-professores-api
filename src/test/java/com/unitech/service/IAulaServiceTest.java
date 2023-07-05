/**
 * 
 */
package com.unitech.service;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unitech.entity.Aula;
import com.unitech.repository.AulaRepository;
import com.unitech.service.exceptions.ObjectNotFoundException;
import com.unitech.service.impl.AulaService;



/**
 * Classe de Teste de Serviços de Aula.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class IAulaServiceTest {

	@Autowired
	private AulaRepository repository; //usado apenas no teste06, deletar todos os registros
	
	@Autowired
	private AulaService service;
	

	
	@Test
	@DisplayName("Deve salvar uma Aula")
	void teste01() {
		
		Aula obj1 = new Aula("Portugues", "1º fundamental", null);
		obj1 = service.save(obj1);
		Assertions.assertEquals(1, obj1.getId());
		Assertions.assertNotNull(obj1.getDate());
		
		//set ID para sequencia conforme registros no BD
		Aula obj2 = new Aula("Matemática", "2º fundamental", new Date());
		obj2.setId(0);
		obj2 = service.save(obj2);
		Assertions.assertEquals(2, obj2.getId());
	}
	
	@Test
	@DisplayName("Deve atualizar uma Aula")
	void teste02() {
		
		//ignora ID e Date
		Aula obj1 = new Aula("Ciências", "3º fundamental", null);
		obj1 = service.update(1L,obj1);
		Assertions.assertEquals("Ciências", obj1.getTitulo());
		
		//ignora alteração no ID do objeto
		Aula obj2 = new Aula("Geografia", "4º fundamental", new Date());
		obj2.setId(0);
		obj2 = service.update(2L, obj2);
		Assertions.assertEquals(2, obj2.getId());
	}
	
	@Test
	@DisplayName("Deve buscar uma Aula por ID")
	void teste03() {
		Aula obj = service.findById(1L);
		Assertions.assertEquals("Ciências", obj.getTitulo() );
		
		//verifica o disparo de exception
		Assertions.assertThrows
			( ObjectNotFoundException.class, () -> service.findById(5L) );
	}
	
	@Test
	@DisplayName("Deve buscar todos os registros de Aula")
	void teste04() {
		List<Aula> lista = service.findAll();
		Assertions.assertEquals(2, lista.size() );
	}
	
	@Test
	@DisplayName("Deve deletar um registro de Aula")
	void teste05() {
		service.delete(1L);
		Assertions.assertThrows
		( ObjectNotFoundException.class, () -> service.findById(1L) );
		
		List<Aula> lista = service.findAll();
		Assertions.assertEquals(1, lista.size() );
	}
	
	@Test
	@DisplayName("Deve deletar todos os registros de Aula")
	void teste06() {
		repository.deleteAll();   
		Assertions.assertEquals(0, service.findAll().size());
	}

}
