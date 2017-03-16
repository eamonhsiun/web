package com.wemeow.web.user.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.TokenDao;
import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;
import com.ymxiong.open.util.ip.IpLongUtils;


@Service
public class TokenService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenDao tokenDao;


	public Token createNewTokenForUser(User user,String IMEI,String ip) {
		Token token = new Token();
		token.setUserId(user.getId());
		token.setIMEI(IMEI);
		token.setIpLong(IpLongUtils.ip2Long(ip));
		tokenDao.insertNewToken(token);
		//设置新id
		user.setTokenId(token.getId());
		userDao.updateTokenId(user);
		return token;
	}
	
}
