package com.dev.blog.helper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dev.blog.services.MyUserDetailsService;


@Component
public class JwtTokenFilter extends OncePerRequestFilter{
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
	)throws ServletException, IOException {
		
		//First check the token from the header from Authorization Bearer token
		//Get authorization header and validate
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(!StringUtils.hasText(header)|| !header.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return; 
			}
			
			//Get jwt token and validate
			String token = header.split(" ")[1].trim();
			if(jwtTokenUtil.validate(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			//Get user identity & set it on the spring security context
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsService, null,userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			//Seting on security context 
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			filterChain.doFilter(request, response);
	}

}
