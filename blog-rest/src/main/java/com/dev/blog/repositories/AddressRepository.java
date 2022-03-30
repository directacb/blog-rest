package com.dev.blog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dev.blog.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long>{

}
