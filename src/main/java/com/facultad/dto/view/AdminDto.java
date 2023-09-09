package com.facultad.dto.view;

import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

	private String id;
	private String name;
	private String email;
	private String phone;
	private String identificationNumber;
    private List<String> roles = new ArrayList<>();
}
