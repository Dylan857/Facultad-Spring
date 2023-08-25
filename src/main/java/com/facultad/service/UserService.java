package com.facultad.service;

import java.util.List;

import com.facultad.dto.UserDto;
import com.facultad.model.User;

public interface UserService {

	public boolean createUser(UserDto userDto);
	
	public boolean login(String email, String password);
	
	public List<User> getUsers();
	
	public List<User> getUsersStudents();
	
	public List<User> getUsersTeachers();
	
	public List<User> getUsersAdmin();
}
