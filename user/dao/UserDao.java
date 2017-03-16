package com.wemeow.web.user.dao;

import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.User;



@Repository
public interface UserDao {
	User selectById(int id);
	User selectByPhone(String phone);
	void updateTokenId(User user); 
}
