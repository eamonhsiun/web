package com.wemeow.web.message.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;
import com.wemeow.web.util.state.StatusException;
import com.ymxiong.open.util.security.SecurityFactory;


@Service
public class Msg0xA2Service {
	

	@Autowired
	private UserDao userDao;
	
	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
	public User getUserById(int id){
		User user =userDao.selectById(id);
		return user;
	}
	
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public User getUserByPhone(String phone)throws StatusException{
		User user =userDao.selectByPhone(phone);
		return user;
	}
	
	public boolean checkPasswordRight(User user,String password){
		String result = SecurityFactory.getCodeMothod("MD5").encode(password+user.getSalt());
		return result.equals(user.getPassword());
	}


	public boolean checkAccountSecuity(String IMEI, String ip) {
		return false;
	}
	
	
	public Token createNewTokenForUse(){
		
		return null;
	}
	
	
	
//
//
//	public void checkPassword(User user, String password) throws MeowException {
//		if(user==null)throw new MeowException(StatusCode.USER_NULL);
//		if(password.length()!=32)throw new MeowException(StatusCode.PASSWORD_LENGTH_ERROR);
//		if(!MD5Utils.MD5(password+user.getSalt()).equals(user.getPassword()))throw new MeowException(StatusCode.PASSWORD_ERROR);		
//	}
	
}
