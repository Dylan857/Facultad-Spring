package com.facultad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.dto.TutoringSessionDto;
import com.facultad.dto.view.TutoringSessionView;
import com.facultad.service.TutoringSessionService;
import com.facultad.util.Response;
import com.facultad.util.annotations.AdminToken;
import com.facultad.util.annotations.TeacherToken;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tutoringSession")
public class TutoringSessionController {

	@Autowired
	private TutoringSessionService tutoringSessionService;

	@GetMapping("/get_tutorings")
	@AdminToken
	public ResponseEntity<Object> getTutoringsSessions() {
		ResponseEntity<Object> response;
		Response responseData = new Response();

		List<TutoringSessionView> tutorings = tutoringSessionService.getTutoringSessions();
		if (!tutorings.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(tutorings);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay datos a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@PostMapping("/create_tutoring")
	@TeacherToken
	public ResponseEntity<Object> createtutoringSession(@Valid @RequestBody TutoringSessionDto tutoringSessionDto) {

		ResponseEntity<Object> response;
		Response responseData = new Response();

		boolean newTutoring = tutoringSessionService.createTutorial(tutoringSessionDto);
		if (newTutoring) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseData.setMessage("Hubo un problema a la hora de crear la tutoria");
			response = new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
			return response;
		}
	}
	
	@PutMapping("/update_tutorial/{id}")
	@TeacherToken
	public ResponseEntity<Object> createtutoringSession(@PathVariable String id, @Valid @RequestBody TutoringSessionDto tutoringSessionDto) {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		boolean updateTutoring = tutoringSessionService.updateTutorial(id, tutoringSessionDto);
		if (updateTutoring) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseData.setMessage("Tutoring id no found");
			response = new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@DeleteMapping("/delete_tutorial/{id}")
	@TeacherToken
	public ResponseEntity<Object> deleteTutorial(@PathVariable String id) {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		boolean deleteTutorial = tutoringSessionService.deleteTutorial(id);
		if (deleteTutorial) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseData.setMessage("Tutoring id no found");
			response = new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
			return response;
		}
	}
}
