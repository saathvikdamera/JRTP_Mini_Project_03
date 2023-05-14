package com.damera.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damera.binding.CommentForm;
import com.damera.constants.AppConstants;
import com.damera.entity.BlogsEntity;
import com.damera.entity.CommentsEntity;
import com.damera.repo.BlogsRepository;
import com.damera.repo.CommentsRepository;
import com.damera.service.IndexService;

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
		
		BlogsEntity blog = null;
		
		Optional<BlogsEntity> findById = blogRepo.findById(blogId);
		if(findById.isPresent()) {
			blog = findById.get();
		}

		return blog;
	}
	
	@Override
	public List<CommentsEntity> addComment(CommentForm form) {
		
		Integer blogId = form.getBlogId();
		BlogsEntity blog = null;
		
		Optional<BlogsEntity> findById = blogRepo.findById(blogId);
		if(findById.isPresent()) {
			blog = findById.get();
		}
		
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
