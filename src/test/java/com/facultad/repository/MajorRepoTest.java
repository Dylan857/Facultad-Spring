package com.facultad.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.facultad.model.Major;

@DataJpaTest //Probar componentes solamente de la capa de persistencia (Solamente a la capa de repositorio)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MajorRepoTest {

	@Autowired
	private MajorRepo majorRepo;
	
	private Major major;
	
	@BeforeEach //Se ejecuta antes de metodo
	void setUp() {
		major = new Major("Idiomas extranjeros");
	}
	
	@Test
	void testSaveMajor() {
		//Given - Dado o condicion previa o configuracion
		Major major1 = new Major("Matematica financiera");
		
		//When - accion o el comportamiento que vamos a tomar
		Major majorSave = majorRepo.save(major1);
		
		//then - verificar la salida
		assertThat(majorSave).isNotNull();
		assertThat(majorSave.getId()).isNotNull();
		
		//BDD: Es una estrategia de desarrollo por comportamiento, empatizado con el usuario final de tus desarrollos
	}
	
	@Test
	void testListMajors() {
		Major major1 = new Major("Matematica financiera");
		majorRepo.save(major1);
		majorRepo.save(major);
		
		List<Major> majors = majorRepo.findByActive(1);
		
		assertThat(majors).isNotNull();
	}
}
