package com.Damera.service;

import java.util.List;

import com.Damera.binding.CreateBlogForm;
import com.Damera.entity.BlogsEntity;
import com.Damera.entity.CommentsEntity;

public interface BlogsService {

	boolean createBlog(CreateBlogForm form,Integer userId);
	
	List<BlogsEntity> viewBlogs(Integer userId);
	
	List<BlogsEntity> viewUserSearchBlogs(Integer userId,String search);
	
	List<CommentsEntity> getComments(Integer userId);
	
	BlogsEntity getBlog(Integer blogId);
	
	boolean disableBlog(Integer blogId);
	
	boolean deleteComment(Integer commentId);
}
