package com.facultad.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Major {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(length = 50, nullable = false)
	private String name;
	
    @Column(name = "date_reg", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @JsonIgnore
    private LocalDateTime dateReg;
	
    @Column(columnDefinition = "INTEGER DEFAULT 1", nullable = false)
    @JsonIgnore
    private Integer active;
    
    @ManyToMany(mappedBy = "teacherMajor")
    @JsonIgnore
    private Set<User> teachers = new HashSet<>();
}
