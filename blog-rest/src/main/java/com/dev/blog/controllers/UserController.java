package com.dev.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.blog.helper.JwtTokenUtil;
import com.dev.blog.helper.ResonseWrapper;
import com.dev.blog.models.AuthRequest;
import com.dev.blog.models.CreateUserRequest;
import com.dev.blog.models.User;
import com.dev.blog.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService userSer;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid CreateUserRequest userRequest){
		
	
		ResonseWrapper responseWrapper = new ResonseWrapper();
		responseWrapper.setMessage("User registered Successfully..");
		responseWrapper.setData(this.userSer.register(userRequest));
		
		return new ResponseEntity<>(responseWrapper,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
		System.out.println(authRequest.getEmail());
		try {
			
			Authentication authentication = authenticationManager.authenticate(
					
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
			);
			
			User user = (User) authentication.getPrincipal();
			
			ResonseWrapper responseWrapper = new ResonseWrapper();
			responseWrapper.setMessage("User login successful.");
			responseWrapper.setData(jwtTokenUtil.generateToken(user));
			return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest){
//		try {
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
//					
//							);
//			
//			User user = (User) authentication.getPrincipal();
//					ResonseWrapper responseWrapper = new ResonseWrapper();
//					responseWrapper.setMessage("User login Successfully..");
//
//					responseWrapper.setData(jwtTokenUtil.generateToken(user));
//					
//					return new ResponseEntity<>(responseWrapper,HttpStatus.OK);
//		}catch (BadCredentialsException ex) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		
//	}
}
