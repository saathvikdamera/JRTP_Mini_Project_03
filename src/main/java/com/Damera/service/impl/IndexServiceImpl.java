package com.Damera.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Damera.binding.CommentForm;
import com.Damera.constants.AppConstants;
import com.Damera.entity.BlogsEntity;
import com.Damera.entity.CommentsEntity;
import com.Damera.repo.BlogsRepository;
import com.Damera.repo.CommentsRepository;
import com.Damera.service.IndexService;

@Service
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	private BlogsRepository blogRepo;
	
	@Autowired
	private CommentsRepository commentRepo;

	@Override
	public List<BlogsEntity> getBlogs() {
		
		List<BlogsEntity> blogs = blogRepo.findByStatus(AppConstants.BLOG_ACTIVE_STATUS);
		
		return blogs;
	}
	
	@Override
	public BlogsEntity getBlog(Integer blogId) {
		BlogsEntity blog = blogRepo.findById(blogId).get();
		return blog;
	}
	
	@Override
	public List<CommentsEntity> addComment(CommentForm form) {
		
		Integer blogId = form.getBlogId();
		BlogsEntity blog = blogRepo.findById(blogId).get();
		
		CommentsEntity comment = new CommentsEntity();
		BeanUtils.copyProperties(form, comment);
		
		comment.setBlog(blog);
		
	    commentRepo.save(comment);
	    
	    List<CommentsEntity> comments = commentRepo.findByBlog(blog);
	    
		return comments;
	}
	
	@Override
	public List<BlogsEntity> getFilteredBlogs(String search) {
		
		List<BlogsEntity> blogs = blogRepo.findByBlogTitleContainingIgnoreCase(search);
		
		return blogs;
	}

}
