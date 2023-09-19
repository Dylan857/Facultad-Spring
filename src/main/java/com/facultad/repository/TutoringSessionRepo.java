package com.facultad.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Major;
import com.facultad.model.TutoringSession;
import com.facultad.model.User;

@Repository
public interface TutoringSessionRepo extends JpaRepository<TutoringSession, String>{

	public List<TutoringSession> findByActive(Integer active);
	public List<TutoringSession> findByTeacherId(User teacherId);
	public List<TutoringSession> findByDate(LocalDate date);
	public List<TutoringSession> findByMajorId(Major majorId);
}
