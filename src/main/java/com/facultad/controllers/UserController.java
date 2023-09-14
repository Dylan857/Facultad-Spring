package com.facultad.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.dto.view.AdminDto;
import com.facultad.dto.view.StudentDto;
import com.facultad.dto.view.TeacherDto;
import com.facultad.model.User;
import com.facultad.service.UserService;
import com.facultad.util.Response;
import com.facultad.util.ResponseOneData;
import com.facultad.util.annotations.AdminToken;
import com.facultad.util.annotations.TeacherToken;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user_information")
	public ResponseEntity<Object> getUserInformation() {
		ResponseEntity<Object> response;
		ResponseOneData responseOneData = new ResponseOneData();
		
		Map<String, Object> user = userService.getUserInformation();
		if (!user.isEmpty()) {
			responseOneData.setStatusCode(HttpStatus.OK.value());
			responseOneData.setMessage("OK");
			responseOneData.setData(user);
			response = new ResponseEntity<>(responseOneData, HttpStatus.OK);
			return response;
		} else {
			responseOneData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseOneData.setMessage("No se ha iniciado sesion");
			response = new ResponseEntity<>(responseOneData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@GetMapping("/get_users")
	@AdminToken
	public ResponseEntity<Object> getUsers() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<User> users = userService.getUsers();
		if (!users.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(users);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay datos a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@GetMapping("/get_users_students")
	@TeacherToken
	public ResponseEntity<Object> getUsersStudents() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<StudentDto> users = userService.getUsersStudents();
		if (!users.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(users);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay datos a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@GetMapping("/get_users_teachers")
	public ResponseEntity<Object> getUsersTeachers() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<TeacherDto> users = userService.getUsersTeachers();
		if (!users.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(users);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay datos a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
	
	@GetMapping("/get_users_admins")
	@AdminToken
	public ResponseEntity<Object> getUsersAdmin() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<AdminDto> users = userService.getUsersAdmin();
		if (!users.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(users);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			return response;
		} else {
			responseData.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseData.setMessage("No hay datos a mostrar");
			response = new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
			return response;
		}
	}
}
