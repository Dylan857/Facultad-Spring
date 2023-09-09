package com.facultad.service;

import java.util.List;

import com.facultad.dto.TutoringSessionDto;
import com.facultad.dto.view.TutoringSessionView;
import com.facultad.model.TutoringSession;

public interface TutoringSessionService {

	public boolean createTutorial(TutoringSessionDto tutoringSessionDto);
	
	public List<TutoringSessionView> getTutoringSessions();
	
	public List<TutoringSession> findTutoringSessionByTeacher(String identificationNumber);
	
	public List<TutoringSession> findTutoringSessionByDate(String date);
	
	public List<TutoringSession> findTutoringSessionByMajor(String majorId);
	
	public List<TutoringSession> findTutoringSessionById(String id);
	
	public boolean updateTutorial(String id, TutoringSessionDto tutoringSessionDto);
	
	public boolean deleteTutorial(String id);
}
