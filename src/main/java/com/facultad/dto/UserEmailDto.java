package com.facultad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailDto {

	private String name;
	private int codeVerification;
	
	public UserEmailDto(String name) {
		super();
		this.name = name;
	}
	
	
}
