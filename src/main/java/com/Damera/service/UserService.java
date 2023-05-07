package com.Damera.service;

import com.Damera.binding.LoginForm;
import com.Damera.binding.RegistrationForm;

public interface UserService {

	String registerUser(RegistrationForm form);
	
	boolean loginUser(LoginForm form);
	
}
