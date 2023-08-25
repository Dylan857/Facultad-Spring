package com.facultad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facultad.model.Course;
import com.facultad.service.CourseService;
import com.facultad.util.Response;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/get_courses")
	public ResponseEntity<Object> getCourses() {
		ResponseEntity<Object> response;
		Response responseData = new Response();
		
		List<Course> courses = courseService.courses();
		if(!courses.isEmpty()) {
			responseData.setStatusCode(HttpStatus.OK.value());
			responseData.setMessage("OK");
			responseData.setData(courses);
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
