package com.facultad.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facultad.model.User;
import com.facultad.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	private User userDetail;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepo.findByEmail(username);
		return new org.springframework.security.core.userdetails.User(userOptional.orElse(null).getEmail(), 
				userOptional.orElse(null).getPassword(), new ArrayList<>());
	}
	
	public User getUserDetail() {
		return userDetail;
	}
}
