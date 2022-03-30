package com.dev.blog.projections;

import org.springframework.data.rest.core.config.Projection;

import com.dev.blog.models.Address;
import com.dev.blog.models.User;

@Projection(name = "UserWithAddress", types = {User.class})
public interface UserWithAddress {
	Long getId();
	
	String getName();
	
	String getEmail();
	
	Address getAddress();

}
