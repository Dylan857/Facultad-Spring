package com.facultad.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(length = 15, nullable = false, unique = true)
	private String phone;
	
	@Column(length = 5, nullable = false)
	@JsonIgnore
	private String idType;
	
	@Column(length = 15, nullable = false, unique = true)
	private String identificationNumber;
	
	@Column(length = 255, nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(name = "date_reg", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonIgnore
	private LocalDateTime dateReg;
	
    @Column(columnDefinition = "INTEGER DEFAULT 1")
    @JsonIgnore
    private Integer active;
    
    @Column(length = 6, nullable = false, unique = true, name = "verification_code")
    @JsonIgnore
    private Integer verificationCode;
    
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name = "teacher_major", joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "major_id", referencedColumnName = "id"))
    private Set<Major> teacherMajor = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> studentCourse = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> teacherCourse = new HashSet<>();

	public User(String name, String email, String phone, String idType, String identificationNumber, String password,
			Integer verificationCode) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.idType = idType;
		this.identificationNumber = identificationNumber;
		this.password = password;
		this.verificationCode = verificationCode;
		this.active = 0;
		this.dateReg = LocalDateTime.now();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles().stream().map(rol -> 
		new SimpleGrantedAuthority(rol.getRole())).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
