package com.facultad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

	public List<User> findByActive(Integer active);
	public User findByIdentificationNumber(String identificationNumber);
	public Optional<User> findById(String id);
	public Optional<User> findByEmail(String email);
	
}
