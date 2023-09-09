package com.facultad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.dto.RequestDto;
import com.facultad.model.Request;
import com.facultad.service.RequestService;
import com.facultad.util.Response;

@RestController
@RequestMapping("/request")
public class RequestController {

	@Autowired
	private RequestService requestService;
	
	@GetMapping("/get_requests")
	public ResponseEntity<Object> getRequests() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<Request> requests = requestService.getRequests();
		if (!requests.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(requests);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay resultados a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@GetMapping("/get_requests_by_teacher/{id}")
	public ResponseEntity<Object> getRequestsByTeacher(@PathVariable String id) {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<Request> requests = requestService.getRequestsByTeacher(id);
		if (!requests.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(requests);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay resultados a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	

	@PostMapping("/make_a_request")
	public ResponseEntity<Object> makeARequest(@RequestBody RequestDto requestDto) {
		ResponseEntity<Object> response;
		Response responseData = new Response();

		boolean newRequest = requestService.makeARequest(requestDto);

		if (newRequest) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseData.setMessage("Hubo un problema a la hora de hacer la solicitud");
			response = new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
			return response;
		}
	}
}
