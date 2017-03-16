package com.wemeow.web.user.dao;

import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.Password;



@Repository
public interface PasswordDao {
	//User selectById(int uid);
	//User selectByName(String name);
	
	void insertNewPassword(Password password);
	
}
