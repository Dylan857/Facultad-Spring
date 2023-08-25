package com.facultad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.TutoringSession;

@Repository
public interface TutoringSessionRepo extends JpaRepository<TutoringSession, String>{

}
