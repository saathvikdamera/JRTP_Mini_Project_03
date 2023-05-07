package com.Damera.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Damera.entity.UserDtlsEntity;

public interface UserDtlsRepository extends JpaRepository<UserDtlsEntity, Integer> {
	
	UserDtlsEntity findByEmail(String email);

	UserDtlsEntity findByEmailAndPassword(String email,String password);
}
