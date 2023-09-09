package com.facultad.service;

import java.util.List;

import com.facultad.dto.UserDto;
import com.facultad.dto.view.AdminDto;
import com.facultad.dto.view.StudentDto;
import com.facultad.dto.view.TeacherDto;
import com.facultad.model.User;

public interface UserService {

	public boolean createUser(UserDto userDto);
	
	public boolean login(String email, String password);
	
	public List<User> getUsers();
	
	public List<StudentDto> getUsersStudents();
	
	public List<TeacherDto> getUsersTeachers();
	
	public List<AdminDto> getUsersAdmin();
	
}
