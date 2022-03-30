package com.dev.blog.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.blog.models.CreateUserRequest;
import com.dev.blog.models.User;
import com.dev.blog.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	public User register(CreateUserRequest userRequest) {
		if(this.userRepo.findByEmail(userRequest.getEmail()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with this email addready exist");
		}
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		
		return userRepo.save(user);
		
		
	}
}
