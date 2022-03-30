package com.dev.blog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dev.blog.models.Blog;

public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {

}
