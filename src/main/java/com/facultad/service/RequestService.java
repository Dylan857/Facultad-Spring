package com.facultad.service;

import java.util.List;

import com.facultad.model.Request;

public interface RequestService {
	
	public List<Request> getRequests();
	
	public List<Request> getRequestsByTeacher(String teacherId);
	
	public boolean makeARequest(String estudianteId, String docenteId, String descripcionSolicitud);
}
