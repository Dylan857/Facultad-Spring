package com.facultad.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.facultad.model.Major;
import com.facultad.service.MajorService;

@SpringBootTest
@WebAppConfiguration
public class MajorControllerTest {

	private MockMvc mockMvc; //Se usa para poder hacer peticiones http
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private MajorService majorService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testMajorList() throws Exception {
		//given
		List<Major> majors = new ArrayList<>();
		Major major = new Major("Idiomas extranjeros");
		Major major1 = new Major("Matematica financiera");
		majors.add(major);
		majors.add(major1);
		given(majorService.getMajors()).willReturn(majors);
		
		//when
		
		ResultActions response = mockMvc.perform(get("/major/get_majors"));
		//then
		response.andExpect(status().isOk());
	}
}
