package com.facultad.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutoringSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private User teacherId;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private Time startTime;

	@Column(nullable = false)
	private Time endTime;

	@ManyToOne
	@JoinColumn(name = "major_id", nullable = false)
	private Major majorId;

	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private StatusTutoringSession statusTutoringSession;

	@Column(name = "date_reg", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonIgnore
	private LocalDateTime dateReg;

	@Column(columnDefinition = "INTEGER DEFAULT 1", nullable = false)
	@JsonIgnore
	private Integer active;

	@ManyToMany
	@JoinTable(name = "students_tutoring", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tutoring_id", referencedColumnName = "id"))
	private Set<User> students = new HashSet<>();

	@Column(name = "topic_covered", length = 30, nullable = false)
	private String topicCovered;

	public TutoringSession(User teacherId, LocalDate date, Time startTime, Time endTime, Major majorId,
			Set<User> students, String topicCovered) {
		super();
		this.teacherId = teacherId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.majorId = majorId;
		this.dateReg = LocalDateTime.now();
		this.active = 1;
		this.students = students;
		this.topicCovered = topicCovered;
	}

}
