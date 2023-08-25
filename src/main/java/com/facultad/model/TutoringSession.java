package com.facultad.model;

import java.sql.Time;
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
public class TutoringSession {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private User teacherId;
	
	@Column(nullable = false)
	private LocalDateTime date;
	
	@Column(nullable = false)
	private Time startTime;
	
	@Column(nullable = false)
	private Time endTime;
	
	@ManyToOne
	@JoinColumn(name = "major_id", nullable = false)
	private Major majorId;
	
	@Column(name = "date_reg", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonIgnore
	private LocalDateTime dateReg;
	
    @Column(columnDefinition = "INTEGER DEFAULT 1", nullable = false)
    @JsonIgnore
    private Integer active;
    
    @ManyToMany
    @JoinTable(name = "students_tutoring", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "tutoring_id", referencedColumnName = "id"))
    private Set<User> students = new HashSet<>();
}
