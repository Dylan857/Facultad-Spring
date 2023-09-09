package com.facultad.dto.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.facultad.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
	private String id;
	private String name;
	private String email;
	private String phone;
	private String identificationNumber;
    private List<String> roles = new ArrayList<>();
    private Set<Course> courses = new HashSet<>();
}
