package com.facultad.service;

import java.util.List;

import com.facultad.dto.TutoringSessionDto;
import com.facultad.dto.view.TutoringSessionView;

import jakarta.mail.MessagingException;

public interface TutoringSessionService {

	public boolean createTutorial(TutoringSessionDto tutoringSessionDto) throws MessagingException;
	
	public List<TutoringSessionView> getTutoringSessions();
	
	public List<TutoringSessionView> findTutoringSessionByTeacher(String identificationNumber);
	
	public List<TutoringSessionView> findTutoringSessionByDate(String date);
	
	public List<TutoringSessionView> findTutoringSessionByMajor(String majorId);
	
	public TutoringSessionView findTutoringSessionById(String id);
	
	public boolean updateTutorial(String id, TutoringSessionDto tutoringSessionDto);
	
	public boolean deleteTutorial(String id);
}
