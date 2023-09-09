package com.facultad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Request;
import com.facultad.model.User;

@Repository
public interface RequestRepo extends JpaRepository<Request, String>{

	public List<Request> findByActive(Integer active);
	public List<Request> findByTeacherId(User teacherId);
}
