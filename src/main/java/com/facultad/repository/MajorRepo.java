package com.facultad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Major;

@Repository
public interface MajorRepo extends JpaRepository<Major, String>{

	public List<Major> findByActive(Integer active);
	public Optional<Major> findById(String id);
}
