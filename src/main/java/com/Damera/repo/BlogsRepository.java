package com.damera.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.damera.entity.BlogsEntity;
import com.damera.entity.UserDtlsEntity;

public interface BlogsRepository extends JpaRepository<BlogsEntity, Integer> {
	
	List<BlogsEntity> findByUser(UserDtlsEntity user);
	
	List<BlogsEntity> findByStatus(String status);
	
	@Query(value = "Select blog from BlogsEntity blog where blog.blogTitle LIKE %?1%")
	List<BlogsEntity> findByBlogTitleContainingIgnoreCase(String blogTitle);
	
	@Query(value = "Select blog from BlogsEntity blog where blog.blogTitle LIKE %?1% and blog.user = ?2")
	List<BlogsEntity> findByBlogTitleContainingIgnoreCase(String blogTitle,UserDtlsEntity user);

}
