package com.dev.blog.services;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.blog.models.User;
import com.dev.blog.repositories.UserRepository;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired	
	private UserRepository userRepository;
	
	@Override
	@Transactional   //SO That it all run under one transactions otherwise will get the exception which can only be seen by the debug mode..
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return userRepository
				.findByEmail(email)
				.map(user-> {
					System.out.println(user.getPassword());
					return new User(
							user.getEmail(),
							user.getPassword(),
							user.getRoles().stream()
											.map(role-> new SimpleGrantedAuthority(role))
											.collect(Collectors.toList())             //by this converting the role to authority as User details need authority as authentication
							);
				}).orElseThrow(()->{
					return new UsernameNotFoundException("User with Email Not Found");
				});
	}

}
