package com.Damera.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Damera.binding.CommentForm;
import com.Damera.entity.BlogsEntity;
import com.Damera.entity.CommentsEntity;
import com.Damera.service.IndexService;

@Controller
public class IndexController {

	@Autowired
	private IndexService indexService;

	@GetMapping("/")
	public String loadIndex(Model model) {

		List<BlogsEntity> blogs = indexService.getBlogs();
		model.addAttribute("blogs", blogs);

		return "index";
	}

	@GetMapping("/view")
	public String viewBlog(@RequestParam("id")Integer id, Model model) {

		BlogsEntity blog = indexService.getBlog(id);

		model.addAttribute("blog", blog);
		model.addAttribute("commentform",new CommentForm());

		return "viewblog";
	}

	@PostMapping("/comment")
	public String saveComment(CommentForm form,Model model) {
		
		List<CommentsEntity> comments = indexService.addComment(form);
		
		BlogsEntity blog = indexService.getBlog(form.getBlogId());
		blog.setLstOfComments(comments);

		model.addAttribute("blog", blog);
		model.addAttribute("commentform",new CommentForm());

		return "viewblog";
	}
	
	@PostMapping("/indexsearch")
	public String searchBlogs(@RequestParam("search")String search ,Model model) {

		System.out.println(search);
		
		List<BlogsEntity> blogs = indexService.getFilteredBlogs(search);
		model.addAttribute("blogs", blogs);
		

		return "index";
	}

}
