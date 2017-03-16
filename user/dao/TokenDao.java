package com.wemeow.web.user.dao;

import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.Token;

@Repository
public interface TokenDao {
	Token selectById(int id);
	void insertNewToken(Token token);
	
}
