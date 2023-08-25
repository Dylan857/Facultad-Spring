package com.facultad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facultad.model.Course;
import com.facultad.repository.CourseRepo;
import com.facultad.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public List<Course> courses() {
		return courseRepo.findByActive(1);
	}
	
}
