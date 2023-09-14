package com.facultad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.dto.LoginUser;
import com.facultad.dto.UserDto;
import com.facultad.security.jwt.AuthResponse;
import com.facultad.service.UserService;
import com.facultad.util.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
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
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginUser loginUser) {
		ResponseEntity<Object> response;
		AuthResponse authResponse = new AuthResponse();
		
		String user = userService.login(loginUser);
		if (user != null) {
			authResponse.setStatusCode(HttpStatus.OK.value());
			authResponse.setMessage("OK");
			authResponse.setToken(user);
			response = new ResponseEntity<>(authResponse, HttpStatus.OK);
			return response;
		} else {
			authResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			authResponse.setMessage("Invalid email or non-active user");
			response = new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
			return response;
		}
	}
}
