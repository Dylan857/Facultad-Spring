package com.facultad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.Request;

@Repository
public interface RequestRepo extends JpaRepository<Request, String>{

}
