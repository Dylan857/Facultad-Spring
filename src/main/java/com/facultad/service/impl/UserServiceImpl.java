package com.facultad.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facultad.dto.LoginUser;
import com.facultad.dto.UserDto;
import com.facultad.dto.view.AdminDto;
import com.facultad.dto.view.StudentDto;
import com.facultad.dto.view.TeacherDto;
import com.facultad.model.Course;
import com.facultad.model.Major;
import com.facultad.model.Role;
import com.facultad.model.User;
import com.facultad.repository.CourseRepo;
import com.facultad.repository.MajorRepo;
import com.facultad.repository.RoleRepo;
import com.facultad.repository.UserRepo;
import com.facultad.security.jwt.JwtService;
import com.facultad.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private MajorRepo majorRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_STUDENT = "ROLE_STUDENT";
	private static final String ROLE_TEACHER = "ROLE_TEACHER";

	@Override
	@Transactional
	public boolean createUser(UserDto userDto) {

		int codigoVerificacion = this.generateCode();
		User newUser = this.newUser(userDto, codigoVerificacion);

		if (userDto.getRole().contains(ROLE_STUDENT) && !validateCourse(userDto.getCourseId())) {
	        return false;
	    }
	    
	    if (userDto.getRole().contains(ROLE_TEACHER) && (!validateMajor(userDto.getMajors()) || !validateCourse(userDto.getCourseId()))) {
	        return false;
	    }
		
		if (userDto.getRole().contains(ROLE_ADMIN)) {
			this.saveRoles(userDto.getRole(), newUser);
			
			if (userDto.getRole().contains(ROLE_STUDENT)) {
				this.saveCourse(userDto.getCourseId(), newUser);
			} else if (userDto.getRole().contains(ROLE_TEACHER)) {
				this.saveCourseTeacher(userDto.getCourseId(), newUser);
				this.saveMajor(userDto.getMajors(), newUser);
			}

		} else if (userDto.getRole().contains(ROLE_STUDENT)) {
			this.saveRoles(userDto.getRole(), newUser);
			this.saveCourse(userDto.getCourseId(), newUser);
			
		} else if (userDto.getRole().contains(ROLE_TEACHER)) {
			this.saveRoles(userDto.getRole(), newUser);
			this.saveCourseTeacher(userDto.getCourseId(), newUser);
			this.saveMajor(userDto.getMajors(), newUser);
			
		} else {
			return false;
		}
		
		userRepo.save(newUser);
		return true;

	}

	@Override
	public String login(LoginUser loginUser) {
		
		User user = userRepo.findByEmail(loginUser.getUsername()).orElse(null);
		
		if (user == null || user.getActive() != 1) {
			return null;
		}
		
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		
		if (authentication.isAuthenticated()) {
			List<String> roles = this.getRolesByUser(user.getRoles());
			return jwtService.getToken(user.getEmail(), roles);
		} else {
			return null;
		}
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findByActive(1);
	}

	@Override
	public List<StudentDto> getUsersStudents() {
		List<StudentDto> usersStudent = new ArrayList<>();
		Role roleStudent = roleRepo.findByRole(ROLE_STUDENT);
		List<User> usersActive = userRepo.findByActive(1);
		for (User user : usersActive) {
			boolean userStudent = user.getRoles().contains(roleStudent);
			if (userStudent) {
				List<String> roles = this.getRolesByUser(user.getRoles());
				StudentDto student = new StudentDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(),
						user.getIdentificationNumber(), roles, user.getStudentCourse());
				usersStudent.add(student);
			}
		}
		return usersStudent;
	}

	@Override
	public List<TeacherDto> getUsersTeachers() {
		List<TeacherDto> usersTeacher = new ArrayList<>();
		Role roleTeacher = roleRepo.findByRole(ROLE_TEACHER);
		List<User> usersActive = userRepo.findByActive(1);
		for (User user : usersActive) {
			boolean userTeacher = user.getRoles().contains(roleTeacher);
			if (userTeacher) {
				List<String> roles = this.getRolesByUser(user.getRoles());
				TeacherDto teacher = new TeacherDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(),
						user.getIdentificationNumber(), roles, user.getTeacherMajor(), user.getTeacherCourse());
				usersTeacher.add(teacher);
			}
		}
		return usersTeacher;
	}

	@Override
	public List<AdminDto> getUsersAdmin() {
		List<AdminDto> usersAdmin = new ArrayList<>();
		Role roleAdmin = roleRepo.findByRole(ROLE_ADMIN);
		List<User> usersActive = userRepo.findByActive(1);
		for (User user : usersActive) {
			boolean userAdmin = user.getRoles().contains(roleAdmin);
			if (userAdmin) {
				List<String> roles = this.getRolesByUser(user.getRoles());
				AdminDto admin = new AdminDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(),
						user.getIdentificationNumber(), roles);
				usersAdmin.add(admin);
			}
		}
		return usersAdmin;
	}

	private int generateCode() {
		Random random = new Random();
		return random.nextInt(900000) + 100000;
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
	
	private void saveMajor(List<String> majorIds, User newUser) {
		for (String majorId : majorIds) {
			Optional<Major> majorOptional = majorRepo.findById(majorId);
			Major major = majorOptional.orElse(null);
			newUser.getTeacherMajor().add(major);
		}
	}

	private User newUser(UserDto userDto, int codigoVerificacion) {
		return new User(userDto.getName(), userDto.getEmail(), userDto.getPhone(), userDto.getIdType(),
				userDto.getIdentificationNumber(), passwordEncoder.encode(userDto.getPassword()), codigoVerificacion);
	}

	@Override
	public List<String> getRolesByUser(Set<Role> rolesModel) {
		List<String> roles = new ArrayList<>();
		for (Role role : rolesModel) {
			String roleString = role.getRole();
			roles.add(roleString);
		}
		return roles;
	}
	
	private boolean validateMajor(List<String> majors) {
		for (String major : majors) {
			Optional<Major> majorOptional = majorRepo.findById(major);
			Major foundMajor = majorOptional.orElse(null);
			if (foundMajor == null) {
				return false;
			}
		}
		return true;
	}
	
	private boolean validateCourse(String courseId) {
		Optional<Course> courseOptional = courseRepo.findById(courseId);
		Course course = courseOptional.orElse(null);
		if (course == null) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getUserInformation() {
		Map<String, Object> userInformation = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
			User foundUser = userOptional.orElse(null);
			
			if (foundUser != null) {
				List<String> roles = this.getRolesByUser(foundUser.getRoles());
				userInformation.put("name", foundUser.getName());
				userInformation.put("email", foundUser.getEmail());
				userInformation.put("phone", foundUser.getPhone());
				userInformation.put("identificationNumber", foundUser.getIdentificationNumber());
				userInformation.put("roles", roles);
				if (roles.contains(ROLE_STUDENT)) {
					userInformation.put("course", foundUser.getStudentCourse());
				} else if (roles.contains(ROLE_TEACHER)) {
					userInformation.put("course", foundUser.getTeacherCourse());
					userInformation.put("major", foundUser.getTeacherMajor());
				}
				return userInformation;
			} else {
				return Collections.emptyMap();
			}
		} else {
			return Collections.emptyMap();
		}
	}
}
