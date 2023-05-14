package com.damera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.damera.binding.LoginForm;
import com.damera.binding.RegistrationForm;
import com.damera.constants.AppConstants;
import com.damera.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/login")
	public String loadLogin(Model model) {
		model.addAttribute("loginform",new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("loginform") LoginForm form,Model model) throws Exception {
		
		boolean status = service.loginUser(form);
		
		if(status) {
			return "redirect:/dashboard";
		}else {
			model.addAttribute("error",AppConstants.INVALID_CREDENTIALS_MSG);
			return "login";
		}
		
	}
	
	@GetMapping("/register")
	public String loadRegistration(Model model) {
		model.addAttribute("registerform", new RegistrationForm());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("registerform") RegistrationForm form,Model model) throws Exception {
		
		String status = service.registerUser(form);
		
		if(status.equals(AppConstants.REGISTRATION_SUCC_MSG)) {
			model.addAttribute("success",status);
		}else {
			model.addAttribute("error", status);
		}
		
		return "register";
	}

}
