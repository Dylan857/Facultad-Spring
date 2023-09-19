package com.facultad.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facultad.model.StatusTutoringSession;

@Repository
public interface StatusTutoringSessionRepo extends JpaRepository<StatusTutoringSession, String> {
	
}
