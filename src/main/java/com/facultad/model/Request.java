package com.facultad.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Request {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private User studentId;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private User teacherId;

	@Column(name = "description_request", nullable = false)
	private String descriptionRequest;
	
	@Column(name = "date_reg", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonIgnore
	private LocalDateTime dateReg;
	
    @Column(columnDefinition = "INTEGER DEFAULT 1", nullable = false)
    @JsonIgnore
    private Integer active;
}
