package com.dev.blog.repositories;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.dev.blog.models.User;
import com.dev.blog.projections.UserWithAddress;

@RepositoryRestResource(excerptProjection = UserWithAddress.class)
//public interface UserRepository extends CrudRepository<User, Long>{
//@RolesAllowed("ADMIN")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	@RestResource(path="find-by-name")    //to change the api url for /users/search/findByName?name=       users/search/find-by-name?name=  
	@RolesAllowed("ADMIN")
//	@RestResource(exported = false)          this by exoprted false this api can not be access by outside it has to be access by controller inside only
	List<User> findByName(String name);
	
	@RestResource(exported = false)
	<S extends User> S save(S entity);
	
	//exported false so that this can not be accessed by outside
	@RestResource(exported = false)
	Optional<User> findByEmail(String email);
}


/*
	
*/