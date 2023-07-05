/**
 * 
 */
package com.unitech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unitech.entity.Aula;
import com.unitech.repository.AulaRepository;
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
	@DisplayName("Deve limpar todos os registros de Aula")
	void deveLimparRegistrosDeAula() {
		repository.deleteAll();   
		
		Assertions.assertEquals(0, service.findAll().size());
	}
	
	@Test
	@DisplayName("Deve salvar uma Aula")
	void deveSalvarUmaAula() {
		Aula a = new Aula("Portugues", "1º fundamental", null);
		a = service.save(a);
		
		Assertions.assertEquals(0, a.getId());
	}

}
