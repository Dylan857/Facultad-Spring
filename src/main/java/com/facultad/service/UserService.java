package com.facultad.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.facultad.dto.LoginUser;
import com.facultad.dto.UserDto;
import com.facultad.dto.view.AdminDto;
import com.facultad.dto.view.StudentDto;
import com.facultad.dto.view.TeacherDto;
import com.facultad.model.Role;
import com.facultad.model.User;

public interface UserService {

	public boolean createUser(UserDto userDto);
	
	public String login(LoginUser loginUser);
	
	public List<User> getUsers();
	
	public List<StudentDto> getUsersStudents();
	
	public List<TeacherDto> getUsersTeachers();
	
	public List<AdminDto> getUsersAdmin();
	
	public Map<String, Object> getUserInformation();
	
	public List<String> getRolesByUser(Set<Role> rolesModel);
	
	public boolean verifyCode(int code);
	
}
