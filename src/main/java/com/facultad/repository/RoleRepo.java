package com.facultad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>{

	public Role findByRole(String role);
	public List<Role> findByActive(Integer active);
}
