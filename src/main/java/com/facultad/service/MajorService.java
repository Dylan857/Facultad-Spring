package com.facultad.service;

import java.util.List;

import com.facultad.model.Major;

public interface MajorService {

	public List<Major> getMajors ();
	public boolean saveMajor(Major major);
}
