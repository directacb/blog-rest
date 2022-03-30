package com.dev.blog.models;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateUserRequest {
	
	
	@Size(min = 5,message = "Minimum 5 characters")
	private String name;
	
	@NotNull(message = "Passwrod is required !")
	@Size(min =8, message = "Minimum 8 charaters")
	private String password;
	
	@NotNull
	@Email
	private String email;
	
	private List<String> roles;
	
	public CreateUserRequest() {
		
	}
	
	

	public CreateUserRequest(String name, String password,String email, List<String> roles) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	
}
