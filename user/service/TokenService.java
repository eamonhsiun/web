package com.wemeow.web.user.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.TokenDao;
import com.wemeow.web.user.entity.Token;
import com.ymxiong.open.util.ip.IpLongUtils;


@Service
public class TokenService {
	
	/**
	 * 最大有效时长
	 */
	//private static final long MAX_VALID_TOKEN = 1000*60*60*60*24*7;
	
	@Autowired
	private TokenDao tokenDao;


	public Token createToken(
			int userId,
			String authc,
			String IMEI,
			String ip
			) {
		Token token = new Token();
		token.setUserId(userId);
		token.setIMEI(IMEI);
		token.setIpLong(IpLongUtils.ip2Long(ip));
		token.setAuthc(authc);
		tokenDao.insertNewToken(token);

		return token;
	}
	
//	/**
//	 * 检测Token
//	 * @param user
//	 * @param token
//	 * @throws StateException
//	 */
//	public void checkToken(User user, int token) throws StatusException {
//		if(user==null)return;
//		if(user.getTokenId()==token)throw new StatusException(StatusCode.PERMISSION_LOW);
//		Date now = new Date();		
//		if((now.getTime()-user.getUpdateTime().getTime())>MAX_VALID_TOKEN){
////			user.setTokenId(0);
////			userDao.updateTokenId(user);
//			throw new StatusException(StatusCode.PERMISSION_LOW);
//		}
//	}
	
	
	
}
