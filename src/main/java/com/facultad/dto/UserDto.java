package com.facultad.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private String idType;
	
	private String identificationNumber;
	
	private String password;
	
	private String courseId;
	
	private List<String> role;
	
	private List<String> majors;
	
	private String courseTeacher;
}
