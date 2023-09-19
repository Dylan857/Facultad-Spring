package com.facultad.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TutoringSessionDto {
	
	@NotBlank
	private String teacherId;
	
	@NotBlank
	private String date;
	
	@NotBlank
	private String startTime;
	
	@NotBlank
	private String endTime;
	
	@NotBlank
	private String majorId;
	
	@NotNull
	private List<String> studentsId;
	
	@NotBlank
	private String topicCovered;
	
	private String statusTutoringSessionId;
}
