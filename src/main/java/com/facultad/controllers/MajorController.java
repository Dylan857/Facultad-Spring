package com.facultad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.model.Major;
import com.facultad.service.MajorService;
import com.facultad.util.Response;

@RestController
@RequestMapping("/major")
public class MajorController {

	@Autowired
	private MajorService majorService;
	
	@GetMapping("/get_majors")
	public ResponseEntity<Object> getMajors() {
		
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<Major> majors = majorService.getMajors();
		if (!majors.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(majors);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay resultados a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
}
