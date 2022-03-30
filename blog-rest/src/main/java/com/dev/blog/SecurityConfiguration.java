package com.dev.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dev.blog.helper.JwtTokenFilter;
import com.dev.blog.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		jsr250Enabled = true
		)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//	return new BCryptPasswordEncoder();
	}
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		auth.inMemoryAuthentication()
//			.withUser("user")
//			.password(encoder.encode("user@123"))
//			.roles("USER")
//			.and()
//			.withUser("admin")
//			.password(encoder.encode("admin@123"))
//			.roles("USER","ADMIN");
		
		auth.userDetailsService(userDetailsService);
	}
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//Disable CSRF 
			http = http.csrf().disable();
			//To disable default cookie based authentication 
			http = http.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and();
			http.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/blogs").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT,"/blogs/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE,"/blogs/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/blogs/**").permitAll()
				.antMatchers(HttpMethod.POST,"/auth/register").permitAll()
				.antMatchers(HttpMethod.POST,"/auth/login").permitAll()
				.anyRequest()
				.authenticated();
//				.and()
//				.httpBasic(); we wont be using basic authentication, 
				//will  use jwt token authentication instead 
				//Add JWT token before Username PasswordAuthenicationFilter
			http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
			
		}
}        
