package com.facultad.validatejwt;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.facultad.model.User;
import com.facultad.repository.UserRepo;
import com.facultad.service.UserService;



@Aspect
@Component
public class JwtAspect {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_STUDENT = "ROLE_STUDENT";
	private static final String ROLE_TEACHER = "ROLE_TEACHER";
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;

	@Before("@annotation(com.facultad.util.annotations.StudentToken)")
	public void validateStudentToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
			User foundUser = userOptional.orElse(null);
			List<String> roles = userService.getRolesByUser(foundUser.getRoles());
			if (roles.contains(ROLE_STUDENT) || roles.contains(ROLE_ADMIN)) {
				//Valida que contiene alguno de los roles y sigue su camino
			} else {
				throw new SecurityException();
			}
		}
	}
	
	@Before("@annotation(com.facultad.util.annotations.TeacherToken)")
	public void validateTeacherToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
			User foundUser = userOptional.orElse(null);
			List<String> roles = userService.getRolesByUser(foundUser.getRoles());
			if (roles.contains(ROLE_TEACHER) || roles.contains(ROLE_ADMIN)) {
				//Valida que contiene alguno de los roles y sigue su camino
			} else {
				throw new SecurityException();
			}
		}
	}
	
	@Before("@annotation(com.facultad.util.annotations.AdminToken)")
	public void validateAdminToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
			User foundUser = userOptional.orElse(null);
			List<String> roles = userService.getRolesByUser(foundUser.getRoles());
			if (!roles.contains(ROLE_ADMIN)) {
				throw new SecurityException();
			}
		}
	}
}
