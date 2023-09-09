package com.facultad.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String phone;
	
	@NotBlank
	private String idType;
	
	@NotBlank
	private String identificationNumber;
	
	@NotBlank
	private String password;
	
	private String courseId;
	
	@NotNull
	private List<String> role;
	
	private List<String> majors;
	
	private String courseTeacher;
}
