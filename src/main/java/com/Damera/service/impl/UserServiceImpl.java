package com.Damera.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Damera.binding.LoginForm;
import com.Damera.binding.RegistrationForm;
import com.Damera.constants.AppConstants;
import com.Damera.entity.UserDtlsEntity;
import com.Damera.repo.UserDtlsRepository;
import com.Damera.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepository userRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public String registerUser(RegistrationForm form) {
		
		UserDtlsEntity entity = userRepo.findByEmail(form.getEmail());
		
		if(entity != null) {
			return AppConstants.EMAIL_EXIST_MSG;
		}
		
		UserDtlsEntity user = new UserDtlsEntity();
		BeanUtils.copyProperties(form, user);
		
		userRepo.save(user);
		
		return AppConstants.REGISTRATION_SUCC_MSG;
	}

	@Override
	public boolean loginUser(LoginForm form) {
		UserDtlsEntity entity = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());

		if(entity == null) {
			return false;
		}
		
		session.setAttribute(AppConstants.SESSION_USERID, entity.getUserId());
		
		return true;
	}

}
