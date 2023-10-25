package com.facultad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import com.facultad.model.Major;
import com.facultad.repository.MajorRepo;
import com.facultad.service.impl.MajorServiceImpl;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MajorServiceTest {

	@Mock //Esa anotacion sirve para crear un simulacro de ese repositorio
	private MajorRepo majorRepo;
	
	@InjectMocks
	private MajorServiceImpl majorService;
	
	private Major major;
	
	@BeforeEach //Se ejecuta antes de metodo
	void setUp() {
		major = new Major("Idiomas extranjeros");
	}
	
	@Test
	void saveMajorTest() {
		//given
		given(majorRepo.save(major)).willReturn(major);
		
		//when
		boolean majorSave = majorService.saveMajor(major);
		
		//then
		assertThat(majorSave).isTrue();
	}
	
	@Test
	void testListMajors() {
		//given
		Major major1 = new Major("Programacion orientado a objetos");
		given(majorRepo.findByActive(1)).willReturn(List.of(major, major1));
		
		//when
		List<Major> majors = majorService.getMajors();
		
		//then
		assertThat(majors).isNotNull();
	}
	
	@Test
	void testListMajorsVoid() {
		//given
		given(majorRepo.findByActive(1)).willReturn(Collections.emptyList());
		
		//when
		List<Major> majors = majorService.getMajors();
		
		//then
		assertThat(majors).isEmpty();
	}
}