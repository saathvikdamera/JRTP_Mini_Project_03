package com.Damera.service;

import java.util.List;

import com.Damera.binding.CommentForm;
import com.Damera.entity.BlogsEntity;
import com.Damera.entity.CommentsEntity;

public interface IndexService {

	List<BlogsEntity> getBlogs();
	
	List<BlogsEntity> getFilteredBlogs(String search);
	
	BlogsEntity getBlog(Integer blogId);
	
	List<CommentsEntity> addComment(CommentForm form);
	
}
