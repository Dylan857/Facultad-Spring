package com.facultad.dto.view;

import java.util.ArrayList;
import java.util.List;

import com.facultad.model.Major;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutoringSessionView {

	private String id;
	private TeacherDto teacherId;
	private String date;
	private String startTime;
	private String endTime;
	private Major majorId;
	private List<StudentDto> students = new ArrayList<>();
	private String topicCovered;
}
