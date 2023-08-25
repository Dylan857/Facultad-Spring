package com.facultad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, String>{

	public List<Course> findByActive(Integer active);
	public Optional<Course> findById(String id);
}
