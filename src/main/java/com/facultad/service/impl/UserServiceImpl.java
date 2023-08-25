package com.facultad.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facultad.dto.UserDto;
import com.facultad.model.Course;
import com.facultad.model.Role;
import com.facultad.model.User;
import com.facultad.repository.CourseRepo;
import com.facultad.repository.RoleRepo;
import com.facultad.repository.UserRepo;
import com.facultad.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public boolean createUser(UserDto userDto) {
		
		int codigoVerificacion = this.generateCode();
		
		if (userDto.getRole().contains("ROLE_ADMIN")) {
			User newUser = this.newUser(userDto, codigoVerificacion);
			this.saveRoles(userDto.getRole(), newUser);
			
			if (userDto.getRole().contains("ROLE_STUDENT")) {
				this.saveCourse(userDto.getCourseId(), newUser);
			} else if (userDto.getRole().contains("ROLE_TEACHER")) {
				this.saveCourseTeacher(userDto.getCourseId(), newUser);
			}
			
			userRepo.save(newUser);
			return true;
		} else if (userDto.getRole().contains("ROLE_STUDENT")) {
			
			User newUser = this.newUser(userDto, codigoVerificacion);
			this.saveRoles(userDto.getRole(), newUser);
			this.saveCourse(userDto.getCourseId(), newUser);
			userRepo.save(newUser);
			return true;
			
		} else if (userDto.getRole().contains("ROLE_TEACHER")) {
			
			User newUser = this.newUser(userDto, codigoVerificacion);
			this.saveRoles(userDto.getRole(), newUser);
			this.saveCourseTeacher(userDto.getCourseId(), newUser);
			userRepo.save(newUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersTeachers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

	private int generateCode() {
		Random random = new Random();
		int codigoVerificacion = random.nextInt(900000) + 100000;
		return codigoVerificacion;
	}
	
	private void saveRoles(List<String> rolesDto, User newUser) {
		for (String role : rolesDto) {
			Role foundRole = roleRepo.findByRole(role);
			newUser.getRoles().add(foundRole);
		}
	}
	
	private void saveCourse(String courseId, User newUser) {
		Optional<Course> courseOptional = courseRepo.findById(courseId);
		if (courseOptional.isPresent()) {
			Course course = courseOptional.get();
			newUser.getStudentCourse().add(course);
		}
	}
	
	private void saveCourseTeacher(String courseId, User newUser) {
		Optional<Course> courseOptional = courseRepo.findById(courseId);
		if (courseOptional.isPresent()) {
			Course course = courseOptional.get();
			newUser.getTeacherCourse().add(course);
		}
	}
	
	private User newUser(UserDto userDto, int codigoVerificacion) {
		User newUser = new User(userDto.getName(), userDto.getEmail(), userDto.getPhone(),
		userDto.getIdType(), userDto.getIdentificationNumber(), userDto.getPassword(), codigoVerificacion);
		return newUser;
	}
}
