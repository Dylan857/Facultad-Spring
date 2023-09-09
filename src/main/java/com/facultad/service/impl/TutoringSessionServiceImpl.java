package com.facultad.service.impl;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facultad.dto.TutoringSessionDto;
import com.facultad.dto.view.StudentDto;
import com.facultad.dto.view.TeacherDto;
import com.facultad.dto.view.TutoringSessionView;
import com.facultad.model.Major;
import com.facultad.model.Role;
import com.facultad.model.TutoringSession;
import com.facultad.model.User;
import com.facultad.repository.MajorRepo;
import com.facultad.repository.TutoringSessionRepo;
import com.facultad.repository.UserRepo;
import com.facultad.service.TutoringSessionService;

@Service
public class TutoringSessionServiceImpl implements TutoringSessionService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TutoringSessionRepo tutoringSessionRepo;

	@Autowired
	private MajorRepo majorRepo;
	
	@Override
	public boolean createTutorial(TutoringSessionDto tutoringSessionDto) {
		User teacher = userRepo.findByIdentificationNumber(tutoringSessionDto.getTeacherId());
		Set<User> students = this.getStudents(tutoringSessionDto.getStudentsId());
		Time startTime = this.getTime(tutoringSessionDto.getStartTime());
		Time endTime = this.getTime(tutoringSessionDto.getEndTime());
		LocalDate date = this.getDate(tutoringSessionDto.getDate());
		Major major = this.getMajor(tutoringSessionDto.getMajorId());
		
		TutoringSession newTutoringSession = new TutoringSession(teacher, date, startTime, endTime, major, students, tutoringSessionDto.getTopicCovered());
		tutoringSessionRepo.save(newTutoringSession);
		return true;
	}

	@Override
	public List<TutoringSessionView> getTutoringSessions() {
		List<TutoringSessionView> tutorings = new ArrayList<>();
		List<TutoringSession> tutoringsModels = tutoringSessionRepo.findByActive(1);
		for (TutoringSession tutoringModel : tutoringsModels) {
			TeacherDto teacher = this.getTeacher(tutoringModel.getTeacherId());
			String formattedDate = this.formatDate(tutoringModel.getDate());
			String formattedStartTime = this.formatTime(tutoringModel.getStartTime());
			String formattedEndTime = this.formatTime(tutoringModel.getEndTime());
			List<StudentDto> students = this.getStudentsTutoring(tutoringModel.getStudents());
			TutoringSessionView tutoringView = new TutoringSessionView(tutoringModel.getId(),teacher, 
			formattedDate, formattedStartTime, formattedEndTime, tutoringModel.getMajorId(), students,
			tutoringModel.getTopicCovered());
			tutorings.add(tutoringView);
		}
		return tutorings;
	}

	@Override
	public List<TutoringSession> findTutoringSessionByTeacher(String identificationNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutoringSession> findTutoringSessionByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutoringSession> findTutoringSessionByMajor(String majorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutoringSession> findTutoringSessionById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTutorial(String id, TutoringSessionDto tutoringSessionDto) {
		Optional<TutoringSession> tutoringSessionOptional = this.tutoringSessionRepo.findById(id);
		TutoringSession tutoringSession = tutoringSessionOptional.orElse(null);
		if (tutoringSession != null && tutoringSession.getActive() == 1) {
			tutoringSession.getStudents().clear();
			User teacher = userRepo.findByIdentificationNumber(tutoringSessionDto.getTeacherId());
			Set<User> students = this.getStudents(tutoringSessionDto.getStudentsId());
			Time startTime = this.getTime(tutoringSessionDto.getStartTime());
			Time endTime = this.getTime(tutoringSessionDto.getEndTime());
			LocalDate date = this.getDate(tutoringSessionDto.getDate());
			Major major = this.getMajor(tutoringSessionDto.getMajorId());
			tutoringSession.setTeacherId(teacher);
			tutoringSession.setStudents(students);
			tutoringSession.setStartTime(startTime);
			tutoringSession.setEndTime(endTime);
			tutoringSession.setDate(date);
			tutoringSession.setMajorId(major);
			tutoringSession.setTopicCovered(tutoringSessionDto.getTopicCovered());
			tutoringSessionRepo.save(tutoringSession);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteTutorial(String id) {
		Optional<TutoringSession> tutoringSessionOptional = this.tutoringSessionRepo.findById(id);
		TutoringSession tutoringSession = tutoringSessionOptional.orElse(null);
		if (tutoringSession != null && tutoringSession.getActive() == 1) {
			tutoringSession.setActive(0);
			tutoringSessionRepo.save(tutoringSession);
			return true;
		} else {
			return false;
		}
	}

	private Set<User> getStudents(List<String> studentsId) {
		Set<User> students = new HashSet<>();
		for (String studentId : studentsId) {
			User student = userRepo.findByIdentificationNumber(studentId);
			students.add(student);
		}
		return students;
	}
	
	private Time getTime(String startTime) {
		LocalTime timeLocalTime = LocalTime.parse(startTime);
		Time sqlTime = Time.valueOf(timeLocalTime);
		return sqlTime;
	}
	
	private LocalDate getDate(String date) {
		LocalDate localDate = LocalDate.parse(date);
		return localDate;
	}
	
	private Major getMajor(String majorId) {
		Optional<Major> majorOptional = majorRepo.findById(majorId);
		Major major = majorOptional.orElse(null);
		return major;
	}
	
	private String formatTime(Time hora) {
		SimpleDateFormat formatoHora12 = new SimpleDateFormat("hh:mm a");
		String formattedTime = formatoHora12.format(hora);
		return formattedTime;
	}
	
	private String formatDate(LocalDate date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = date.format(format);
		return formattedDate;
	}
	
	private List<StudentDto> getStudentsTutoring(Set<User> studentsModel) {
		List<StudentDto> students = new ArrayList<>();
		for (User studentModel : studentsModel) {
			List<String> roles = this.getRolesByUser(studentModel.getRoles());
			StudentDto student = new StudentDto(studentModel.getId(), studentModel.getName(), 
			studentModel.getEmail(), studentModel.getPhone(), 
			studentModel.getIdentificationNumber(), roles, studentModel.getStudentCourse());
			students.add(student);
		}
		return students;
	}
	
	private TeacherDto getTeacher(User teacherModel) {
		List<String> roles = this.getRolesByUser(teacherModel.getRoles());
		TeacherDto teacher = new TeacherDto(teacherModel.getId(), teacherModel.getName(), 
				teacherModel.getEmail(), teacherModel.getPhone(), teacherModel.getIdentificationNumber(), 
				roles, teacherModel.getTeacherMajor(), teacherModel.getTeacherCourse());;
		return teacher;
	}
	
	private List<String> getRolesByUser(Set<Role> rolesModel) {
		List<String> roles = new ArrayList<>();
		for (Role role : rolesModel) {
			String roleString = role.getRole();
			roles.add(roleString);
		}
		return roles;
	}
}
