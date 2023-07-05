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
	private AulaRepository repository; //usado apenas no limpar deveLimparRegistrosDeAula
	
	@Autowired
	private AulaService service;
	

	
	@Test
	@DisplayName("Deve salvar uma Aula")
	void teste01() {
		
		Aula a1 = new Aula("Portugues", "1º fundamental", null);
		a1 = service.save(a1);
		Assertions.assertEquals(1, a1.getId());
		Assertions.assertNotNull(a1.getDate());
		
		//set ID para sequencia conforme registros no BD
		Aula a2 = new Aula("Matemática", "2º fundamental", new Date());
		a2.setId(0);
		a2 = service.save(a2);
		Assertions.assertEquals(2, a2.getId());
	}
	
	@Test
	@DisplayName("Deve atualizar uma Aula")
	void teste02() {
		
		//ignora ID e Date
		Aula a1 = new Aula("Ciências", "3º fundamental", null);
		a1 = service.update(1L,a1);
		Assertions.assertEquals("Ciências", a1.getTitulo());
		
		//ignora alteração no ID do objeto
		Aula a2 = new Aula("Geografia", "4º fundamental", new Date());
		a2.setId(0);
		a2 = service.update(2L,a2);
		Assertions.assertEquals(2, a2.getId());
	}
	
	@Test
	@DisplayName("Deve buscar uma Aula por ID")
	void teste03() {
		Aula a1 = service.findById(1L);
		Assertions.assertEquals("Ciências", a1.getTitulo() );
		
		//verifica o disparo de exception
		Assertions.assertThrows
			( ObjectNotFoundException.class, () -> service.findById(5L) );
	}
	
	@Test
	@DisplayName("Deve buscar todos os registros de Aula")
	void teste04() {
		List<Aula> aulas = service.findAll();
		Assertions.assertEquals(2, aulas.size() );
	}
	
	@Test
	@DisplayName("Deve deletar um registro de Aula")
	void teste05() {
		service.delete(1L);
		Assertions.assertThrows
		( ObjectNotFoundException.class, () -> service.findById(1L) );
		
		List<Aula> aulas = service.findAll();
		Assertions.assertEquals(1, aulas.size() );
	}
	
	@Test
	@DisplayName("Deve deletar todos os registros de Aula")
	void teste06() {
		repository.deleteAll();   
		Assertions.assertEquals(0, service.findAll().size());
	}

}
