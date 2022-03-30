package com.dev.blog.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AuthRequest {
	
	@NotNull(message = "Email is required")
	@Email(message="Please enter valid email.")
	private String email;
	
	@NotNull(message = "Password is required")
	private String password;
	
	
	public AuthRequest() {
		
	}
	public AuthRequest(String email, String password) {
		this.email=email;
		this.password=password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
