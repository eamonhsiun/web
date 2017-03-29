package com.wemeow.web.user.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.MeowException;
import com.wemeow.web.user.dao.PasswordDao;
import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Password;
import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;
import com.ymxiong.open.util.security.SecurityFactory;


@Service
public class UserService {
	

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordDao passwordDao;
	
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
	public User getUserByPhone(String phone)throws MeowException{
		User user =userDao.selectByPhone(phone);
		return user;
	}
	
	public boolean checkPasswordRight(User user,String password){
		String result = SecurityFactory.getCodeMothod("MD5").encode(password+user.getSalt());
		return result.equals(user.getPassword());
	}


	public boolean isAccountSecuity(String IMEI, String ip) {
		return true;
	}
	
	
	public Token createNewTokenForUse(){

		return null;
	}
	
	public List<Map<String, Object>> getUserFriends(int id){
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<User> list1 =userDao.selectMyFollowerList(id);
		List<User> list2 =userDao.selectMyFollowList(id);
		list1.retainAll(list2);
		list1.stream().forEach((e)->{
			Map<String, Object> item = new HashMap<>();
			item.put("id", e.getId());
			item.put("nickname", e.getNickname());
			resultList.add(item);
		});
		return resultList;
	}
	
	public Object insertFollower(int userId,int followId){
		return userDao.insertFollower(userId, followId);
	}


	public Object register(String phone,String nickname,String pwd, int vCodeId, int vCode) {
		//TODO:Check Code
		
		
		String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
		String codePwd = SecurityFactory.getCodeMothod("MD5").encode(pwd+salt);
		
		User user = new User();
		user.setPhone(phone);
		user.setNickname(nickname);
		
		user.setSalt(salt);
		user.setPassword(codePwd);
		userDao.insertNewUser(user);
		
		Password password = new Password();
		password.setPassword(codePwd);
		password.setSalt(salt);
		password.setUserId(user.getId());
		passwordDao.insertNewPassword(password);

		return user;
	}
	
	
	
//
//
//	public void checkPassword(User user, String password) throws MeowException {
//		if(user==null)throw new MeowException(StatusCode.USER_NULL);
//		if(password.length()!=32)throw new MeowException(StatusCode.PASSWORD_LENGTH_ERROR);
//		if(!MD5Utils.MD5(password+user.getSalt()).equals(user.getPassword()))throw new MeowException(StatusCode.PASSWORD_ERROR);		
//	}
	
}
