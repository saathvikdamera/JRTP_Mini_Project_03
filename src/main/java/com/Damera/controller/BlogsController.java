package com.damera.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.damera.binding.CreateBlogForm;
import com.damera.constants.AppConstants;
import com.damera.entity.BlogsEntity;
import com.damera.entity.CommentsEntity;
import com.damera.service.BlogsService;

@Controller
public class BlogsController {

	@Autowired
	private HttpSession session;

	@Autowired
	private BlogsService blogService;

	@GetMapping("/dashboard")
	public String loadDashboard(Model model) {

		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);

		List<BlogsEntity> blogs = blogService.viewBlogs(userId);

		model.addAttribute("blogs", blogs);

		return "dashboard";
	}

	@GetMapping("/edit")
	public String editBlog(@RequestParam("id") Integer blogId,Model model) {

		BlogsEntity blog = blogService.getBlog(blogId);
		CreateBlogForm form = new CreateBlogForm();

		BeanUtils.copyProperties(blog, form);
		model.addAttribute("createform",blog);

		return "newpost";
	}

	@GetMapping("/deleteblog")
	public String deleteBlog(@RequestParam("id") Integer blogId,Model model) {

		boolean status = blogService.disableBlog(blogId);
		
		if(status) {
			model.addAttribute("success",AppConstants.BLOG_DELETION_SUCC);
		}else {
			model.addAttribute("error",AppConstants.BLOG_DELETION_ERROR);
		}

		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);

		List<BlogsEntity> blogs = blogService.viewBlogs(userId);

		model.addAttribute("blogs", blogs);

		return "dashboard";
	}
	
	@GetMapping("/deletecomment")
	public String deleteComment(@RequestParam("id") Integer commentId,Model model) {
		
		boolean status = blogService.deleteComment(commentId);
		
		if(status) {
			model.addAttribute("success",AppConstants.COMMENT_DELETION_SUCC);
		}else {
			model.addAttribute("error",AppConstants.COMMENT_DELETION_ERROR);
		}
		
		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);

		List<CommentsEntity> comments = blogService.getComments(userId);
		model.addAttribute("comments", comments);

		return "comments";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return"redirect:/";
	}

	@GetMapping("/newpost")
	public String loadNewpost(Model model) {
		model.addAttribute("createform",new CreateBlogForm());
		return "newpost";
	}

	@PostMapping("/newpost")
	public String createPost(CreateBlogForm form,Model model) {

		System.out.println(form);

		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);
		boolean status = blogService.createBlog(form, userId);

		if(status) {
			model.addAttribute("success", AppConstants.BLOG_SUCC_MSG);
		}else {
			model.addAttribute("error", AppConstants.BLOG_ERROR_MSG);
		}
		model.addAttribute("createform",new CreateBlogForm());

		return "newpost";
	}

	@GetMapping("/comments")
	public String loadComments(Model model) {

		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);

		List<CommentsEntity> comments = blogService.getComments(userId);
		model.addAttribute("comments", comments);

		return "comments";
	}

	@GetMapping("/dashboardsearch")
	public String getSearchBlogs(@RequestParam("search")String search,Model model) {
		
		Integer userId = (Integer)session.getAttribute(AppConstants.SESSION_USERID);

		List<BlogsEntity> blogs = blogService.viewUserSearchBlogs(userId, search);
		
		model.addAttribute("blogs", blogs);
		
		return "dashboard-filter";
	}


}
