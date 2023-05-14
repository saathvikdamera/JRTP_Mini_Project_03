package com.damera.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.damera.binding.CreateBlogForm;
import com.damera.constants.AppConstants;
import com.damera.entity.BlogsEntity;
import com.damera.entity.CommentsEntity;
import com.damera.entity.UserDtlsEntity;
import com.damera.repo.BlogsRepository;
import com.damera.repo.CommentsRepository;
import com.damera.repo.UserDtlsRepository;
import com.damera.service.BlogsService;

@Service
public class BlogsServiceImpl implements BlogsService {

	@Autowired
	private UserDtlsRepository userRepo;

	@Autowired
	private BlogsRepository blogRepo;

	@Autowired
	private CommentsRepository commentRepo;

	@Override
	public boolean createBlog(CreateBlogForm form,Integer userId) {

		UserDtlsEntity user = null; 

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);

		if(findById.isPresent()) {
			user = findById.get();
		}

		BlogsEntity blog = new BlogsEntity();

		BeanUtils.copyProperties(form, blog);

		blog.setUser(user);
		blog.setStatus(AppConstants.BLOG_ACTIVE_STATUS);

		blogRepo.save(blog);

		return true;
	}

	@Override
	public List<BlogsEntity> viewBlogs(Integer userId) {

		UserDtlsEntity user = null; 

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);

		if(findById.isPresent()) {
			user = findById.get();
		}


		BlogsEntity blog = new BlogsEntity();
		blog.setUser(user);
		blog.setStatus(AppConstants.BLOG_ACTIVE_STATUS);

		Example<BlogsEntity> example = Example.of(blog);

		List<BlogsEntity> blogs = blogRepo.findAll(example);

		return blogs;
	}

	@Override
	public List<CommentsEntity> getComments(Integer userId) {

		UserDtlsEntity user = null; 

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);

		if(findById.isPresent()) {
			user = findById.get();
		}

		List<CommentsEntity> comments = new ArrayList<>();

		List<BlogsEntity> blogs = blogRepo.findByUser(user);

		for(BlogsEntity blog : blogs) {
			List<CommentsEntity> lstOfComments = blog.getLstOfComments();
			comments.addAll(lstOfComments);
		}

		return comments;
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
	public boolean disableBlog(Integer blogId) {

		BlogsEntity blog = null;

		Optional<BlogsEntity> findById = blogRepo.findById(blogId);

		if(findById.isPresent()) {
			blog = findById.get();
			blog.setStatus(AppConstants.BLOG_INACTIVE_STATUS);
		}

		blogRepo.save(blog);

		return true;
	}

	@Override
	public boolean deleteComment(Integer commentId) {

		Integer rowsAffected = commentRepo.deleteComment(commentId);

		return rowsAffected > 0;
	}

	@Override
	public List<BlogsEntity> viewUserSearchBlogs(Integer userId, String search) {

		UserDtlsEntity user = null; 

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);

		if(findById.isPresent()) {
			user = findById.get();
		}
		List<BlogsEntity> blogs = blogRepo.findByBlogTitleContainingIgnoreCase(search, user);

		return blogs;
	}

}
