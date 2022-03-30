package com.dev.blog.models;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min = 5,message = "Minimum 5 characters")
	@Column()
	private String name;
	
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	/*		Three questions regarding Spring course ?????
	 * 1) Normally in real world data base tables are created by the database team, so how does it work  with Spring where Entity anotation creates its tables 
	 * 2) How we send address and blogs json object from front-end application as we are adding text file (url) as a address or blogs to the user objects ??
	 * 3) How csrf token works  as its safe and we have to have that for ecommerce websites and all ?
	 * 4)--How long this class recordings will be available ?????
	 */
	
	@NotNull
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@CreatedDate
	private Instant createdAt;
	
	@ElementCollection  //Creates separate table so that we dont have to create model...its like one to many relation mapping...
	private List<String> roles;
	
	@LastModifiedDate
	private Instant updatedAt;
	
	@OneToOne
	@JoinColumn(name = "address_id")   //address_id column will be created in the Address table..
	private Address address;
	
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs;
	
	@ManyToMany
	@JoinTable(
			name="book_author",
			joinColumns = @JoinColumn(name="author_id",referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name ="book_id",referencedColumnName = "id")
			)
	private List<Books> books;
	@Transient    //This anotation so that authorities coloum does not get created in the database as the class is marked as @Entity
	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;    //This filed is for the method getAauthorities and that one is because implimanted the interface which authenticate against the username
	
	public User() {
		
	}
	
	public User(Long id, String name, String password, String email) {
	
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public User(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		
		
		this.authorities=authorities;
		this.password = password;
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<Books> getBooks() {
		return books;
	}
	
	

	public void setBooks(List<Books> books) {
		this.books = books;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
		
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	
	
	
	
}
