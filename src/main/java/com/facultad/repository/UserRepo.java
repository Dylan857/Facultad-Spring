package com.facultad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

}
