package com.damera.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damera.binding.LoginForm;
import com.damera.binding.RegistrationForm;
import com.damera.constants.AppConstants;
import com.damera.entity.UserDtlsEntity;
import com.damera.repo.UserDtlsRepository;
import com.damera.service.UserService;
import com.damera.utils.AESPasswordEncrypter;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepository userRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private AESPasswordEncrypter encrypter;

	@Override
	public String registerUser(RegistrationForm form) throws Exception {

		UserDtlsEntity entity = userRepo.findByEmail(form.getEmail());

		if (entity != null) {
			return AppConstants.EMAIL_EXIST_MSG;
		}

		UserDtlsEntity user = new UserDtlsEntity();
		BeanUtils.copyProperties(form, user);

		String encryptedPwd = encrypter.encrypt(form.getPassword(), AppConstants.SECRET_KEY);
		user.setPassword(encryptedPwd);

		userRepo.save(user);

		return AppConstants.REGISTRATION_SUCC_MSG;
	}

	@Override
	public boolean loginUser(LoginForm form) throws Exception {

		UserDtlsEntity userDtlsEntity = userRepo.findByEmail(form.getEmail());

		if (userDtlsEntity == null) {
			return false;
		}

		String decryptedPwd = encrypter.decrypt(userDtlsEntity.getPassword(), AppConstants.SECRET_KEY);

		if (!form.getPassword().equals(decryptedPwd)) {
			return false;
		}

		session.setAttribute(AppConstants.SESSION_USERID, userDtlsEntity.getUserId());

		return true;
		
	}

}
