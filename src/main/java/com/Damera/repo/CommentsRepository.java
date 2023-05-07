package com.Damera.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Damera.entity.BlogsEntity;
import com.Damera.entity.CommentsEntity;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Integer> {

	List<CommentsEntity> findByBlog(BlogsEntity blog);
	
	@Modifying
	@Transactional
	@Query(value = "delete from CommentsEntity where commentId = :commentId")
	Integer deleteComment(@Param("commentId")Integer commentId);

}
