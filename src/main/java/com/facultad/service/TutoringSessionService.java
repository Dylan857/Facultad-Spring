package com.facultad.service;

import java.util.List;

import com.facultad.model.TutoringSession;

public interface TutoringSessionService {

	public boolean createTutorial(String docenteId, String date, String startTime, String endTime, 
	List<String> students, String majorId, String topicCovered);
	
	public List<TutoringSession> getTutoringSessions();
	
	public List<TutoringSession> findTutoringSessionByTeacher(String identificationNumber);
	
	public List<TutoringSession> findTutoringSessionByDate(String date);
	
	public List<TutoringSession> findTutoringSessionByMajor(String majorId);
	
	public List<TutoringSession> findTutoringSessionById(String id);
	
	public boolean updateTutorial(String id, String docenteId, String date, String startTime, String endTime, 
			List<String> students, String majorId, String topicCovered);
	
	public boolean deleteTutorial(String id);
}
