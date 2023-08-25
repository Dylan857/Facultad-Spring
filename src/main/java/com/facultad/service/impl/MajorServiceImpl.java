package com.facultad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facultad.model.Major;
import com.facultad.repository.MajorRepo;
import com.facultad.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {

	@Autowired
	private MajorRepo majorRepo;
	
	@Override
	public List<Major> get_majors() {
		return majorRepo.findByActive(1);
	}

}
