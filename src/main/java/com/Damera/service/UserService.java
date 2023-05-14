package com.damera.service;

import com.damera.binding.LoginForm;
import com.damera.binding.RegistrationForm;

public interface UserService {

	String registerUser(RegistrationForm form) throws Exception;
	
	boolean loginUser(LoginForm form) throws Exception;
	
}
