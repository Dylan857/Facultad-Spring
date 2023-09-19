package com.facultad.service.impl;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.facultad.model.StatusTutoringSession;
import com.facultad.model.TutoringSession;
import com.facultad.model.User;
import com.facultad.repository.MajorRepo;
import com.facultad.repository.StatusTutoringSessionRepo;
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
	
	@Autowired
	private StatusTutoringSessionRepo statusTutoringSessionRepo;
	
	@Override
	public boolean createTutorial(TutoringSessionDto tutoringSessionDto) {
		User teacher = userRepo.findByIdentificationNumber(tutoringSessionDto.getTeacherId());
		Set<User> students = this.getStudents(tutoringSessionDto.getStudentsId());
		Time startTime = this.getTime(tutoringSessionDto.getStartTime());
		Time endTime = this.getTime(tutoringSessionDto.getEndTime());
		LocalDate date = this.getDate(tutoringSessionDto.getDate());
		Major major = this.getMajor(tutoringSessionDto.getMajorId());
		StatusTutoringSession statusTutoringSession = this.getStatusTutoring("1");
		
		TutoringSession newTutoringSession = new TutoringSession(teacher, date, 
				startTime, endTime, major, students, tutoringSessionDto.getTopicCovered());
		newTutoringSession.setStatusTutoringSession(statusTutoringSession);
		tutoringSessionRepo.save(newTutoringSession);
		return true;
	}

	@Override
	public List<TutoringSessionView> getTutoringSessions() {
		List<TutoringSession> tutoringsModels = tutoringSessionRepo.findByActive(1);
		List<TutoringSessionView> tutorings = this.tutoringsToTutoringsDto(tutoringsModels);
		if (!tutorings.isEmpty()) {
			return tutorings;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<TutoringSessionView> findTutoringSessionByTeacher(String identificationNumber) {
		User teacher = userRepo.findByIdentificationNumber(identificationNumber);
		if (teacher != null && teacher.getActive() == 1) {
			List<TutoringSession> tutoringsModels = tutoringSessionRepo.findByTeacherId(teacher);
			List<TutoringSessionView> tutorings = this.tutoringsToTutoringsDto(tutoringsModels);
			if (!tutorings.isEmpty()) {
				return tutorings;
			} else {
				return Collections.emptyList();
			}
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<TutoringSessionView> findTutoringSessionByDate(String date) {
		LocalDate dateLocal = this.getDate(date);
		List<TutoringSession> tutoringsModel = tutoringSessionRepo.findByDate(dateLocal);
		
		if (!tutoringsModel.isEmpty()) {
			List<TutoringSessionView> tutorings = this.tutoringsToTutoringsDto(tutoringsModel);
			if (!tutorings.isEmpty()) {
				return tutorings;
			} else {
				return Collections.emptyList();
			}
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<TutoringSessionView> findTutoringSessionByMajor(String majorId) {
		Major major = this.getMajor(majorId);
		List<TutoringSession> tutoringsModel = tutoringSessionRepo.findByMajorId(major);
		if (!tutoringsModel.isEmpty()) {
			List<TutoringSessionView> tutorings = this.tutoringsToTutoringsDto(tutoringsModel);
			if (!tutorings.isEmpty()) {
				return tutorings;
			} else {
				return Collections.emptyList();
			}
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public TutoringSessionView findTutoringSessionById(String id) {
		Optional<TutoringSession> tutoringOptional = tutoringSessionRepo.findById(id);
		TutoringSession tutoringModel = tutoringOptional.orElse(null);
		if (tutoringModel != null && tutoringModel.getActive() == 1) {
			List<StudentDto> students = this.getStudentsTutoring(tutoringModel.getStudents());
			TeacherDto teacher = this.getTeacher(tutoringModel.getTeacherId());
			String formattedStartTime = this.formatTime(tutoringModel.getStartTime());
			String formattedEndTime = this.formatTime(tutoringModel.getEndTime());
			String formattedDate = this.formatDate(tutoringModel.getDate());
			StatusTutoringSession statusTutoringSession = this.getStatusTutoring(tutoringModel.getStatusTutoringSession().getId());
			return new TutoringSessionView(tutoringModel.getId(),teacher, 
					formattedDate, formattedStartTime, formattedEndTime, tutoringModel.getMajorId(), students,
					tutoringModel.getTopicCovered(), statusTutoringSession.getStatus());
		} else {
			return null;
		}
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
			StatusTutoringSession statusTutoringSession = this.getStatusTutoring(tutoringSessionDto.getStatusTutoringSessionId());
			tutoringSession.setTeacherId(teacher);
			tutoringSession.setStudents(students);
			tutoringSession.setStartTime(startTime);
			tutoringSession.setEndTime(endTime);
			tutoringSession.setDate(date);
			tutoringSession.setMajorId(major);
			tutoringSession.setTopicCovered(tutoringSessionDto.getTopicCovered());
			tutoringSession.setStatusTutoringSession(statusTutoringSession);
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
			StatusTutoringSession statusTutoringSession = this.getStatusTutoring("3");
			tutoringSession.setStatusTutoringSession(statusTutoringSession);
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
		return Time.valueOf(timeLocalTime);
	}
	
	private LocalDate getDate(String date) {
		return LocalDate.parse(date);
	}
	
	private Major getMajor(String majorId) {
		Optional<Major> majorOptional = majorRepo.findById(majorId);
		return majorOptional.orElse(null);
	}
	
	private String formatTime(Time hora) {
		SimpleDateFormat formatoHora12 = new SimpleDateFormat("hh:mm a");
		return formatoHora12.format(hora);
	}
	
	private String formatDate(LocalDate date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(format);
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
		return new TeacherDto(teacherModel.getId(), teacherModel.getName(), 
				teacherModel.getEmail(), teacherModel.getPhone(), teacherModel.getIdentificationNumber(), 
				roles, teacherModel.getTeacherMajor(), teacherModel.getTeacherCourse());
	}
	
	private List<String> getRolesByUser(Set<Role> rolesModel) {
		List<String> roles = new ArrayList<>();
		for (Role role : rolesModel) {
			String roleString = role.getRole();
			roles.add(roleString);
		}
		return roles;
	}
	
	private StatusTutoringSession getStatusTutoring(String id) {
		Optional<StatusTutoringSession> statusTutoringSessionOptional = statusTutoringSessionRepo.findById(id);
		return statusTutoringSessionOptional.orElse(null);
	}
	
	private List<TutoringSessionView> tutoringsToTutoringsDto(List<TutoringSession> tutoringsModels) {
		List<TutoringSessionView> tutorings = new ArrayList<>();
		for (TutoringSession tutoringModel : tutoringsModels) {
			TeacherDto teacher = this.getTeacher(tutoringModel.getTeacherId());
			String formattedDate = this.formatDate(tutoringModel.getDate());
			String formattedStartTime = this.formatTime(tutoringModel.getStartTime());
			String formattedEndTime = this.formatTime(tutoringModel.getEndTime());
			StatusTutoringSession statusTutoringSession = this.getStatusTutoring(tutoringModel.getStatusTutoringSession().getId());
			
			List<StudentDto> students = this.getStudentsTutoring(tutoringModel.getStudents());
			TutoringSessionView tutoringView = new TutoringSessionView(tutoringModel.getId(),teacher, 
			formattedDate, formattedStartTime, formattedEndTime, tutoringModel.getMajorId(), students,
			tutoringModel.getTopicCovered(), statusTutoringSession.getStatus());
			tutorings.add(tutoringView);
		}
		return tutorings;
	}
}
