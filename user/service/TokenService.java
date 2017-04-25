package com.wemeow.web.user.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.TokenDao;
import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;
import com.ymxiong.open.util.ip.IpLongUtils;


@Service
public class TokenService {
	
	/**
	 * 最大有效时长
	 */
	private static final long MAX_VALID_TOKEN = 1000*60*60*60*24*7;
	
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
	
	/**
	 * 检测Token
	 * @param user
	 * @param token
	 * @throws StateException
	 */
	public void checkToken(User user, int token) throws StatusException {
		if(user==null)return;
		if(user.getTokenId()==token)throw new StatusException(StatusCode.PERMISSION_LOW);
		Date now = new Date();		
		if((now.getTime()-user.getUpdateTime().getTime())>MAX_VALID_TOKEN){
			user.setTokenId(0);
			userDao.updateTokenId(user);
			throw new StatusException(StatusCode.PERMISSION_LOW);
		}
	}
	
	
	
}
