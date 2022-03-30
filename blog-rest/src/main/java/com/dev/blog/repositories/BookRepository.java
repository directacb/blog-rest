package com.dev.blog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dev.blog.models.Books;

public interface BookRepository extends CrudRepository<Books, Long> {

}
