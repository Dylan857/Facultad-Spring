package com.facultad.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facultad.dto.RequestDto;
import com.facultad.model.Request;
import com.facultad.model.User;
import com.facultad.repository.RequestRepo;
import com.facultad.repository.UserRepo;
import com.facultad.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestRepo requestRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<Request> getRequests() {
		return requestRepo.findByActive(1);
	}

	@Override
	public List<Request> getRequestsByTeacher(String teacherId) {
		Optional<User> userOptional = userRepo.findById(teacherId);
		User teacher = userOptional.orElse(null);
		if (teacher != null && teacher.getActive() == 1) {
			return requestRepo.findByTeacherId(teacher);
		} else {
			return null;
		}
	}

	@Override
	public boolean makeARequest(RequestDto requestDto) {
		User student = userRepo.findByIdentificationNumber(requestDto.getIdentificationStudent());
		User teacher = userRepo.findByIdentificationNumber(requestDto.getIdentificationTeacher());
		if (student.getActive() == 1 && teacher.getActive() == 1) {
			Request request = new Request(student, teacher, requestDto.getDescriptionRequest());
			requestRepo.save(request);
			return true;
		} else {
			return false;
		}
	}
}
