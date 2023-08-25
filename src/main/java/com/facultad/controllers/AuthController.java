package com.facultad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.dto.UserDto;
import com.facultad.service.UserService;
import com.facultad.util.Response;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		boolean newUser = userService.createUser(userDto);
		
		if (newUser) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseData.setMessage("Hubo un problema a la hora de crear el usuario");
			response = new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
			return response;
		}
	}
}
