package com.facultad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>{

	public Role findByRole(String role);
}
