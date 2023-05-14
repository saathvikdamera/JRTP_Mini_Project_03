package com.damera.service;

import java.util.List;

import com.damera.binding.CommentForm;
import com.damera.entity.BlogsEntity;
import com.damera.entity.CommentsEntity;

public interface IndexService {

	List<BlogsEntity> getBlogs();
	
	List<BlogsEntity> getFilteredBlogs(String search);
	
	BlogsEntity getBlog(Integer blogId);
	
	List<CommentsEntity> addComment(CommentForm form);
	
}
