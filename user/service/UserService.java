package com.wemeow.web.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.PasswordDao;
import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Password;
import com.wemeow.web.user.entity.User;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;
import com.ymxiong.open.util.security.SecurityFactory;


@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordDao passwordDao;
	
	@Autowired
	private CaptchaService captchaService;
	
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
	public User getUserByPhone(String phone){
		User user =userDao.selectByPhone(phone);
		return user;
	}
	
	public boolean isAccountSecuity(String IMEI, String ip) {
		return true;
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
	
	public Map<String, Object> forget(
			String phone, 
			String password, 
			int codeId, 
			int code) throws StatusException {
		//验证code
		captchaService.verify(codeId, code, phone);
		User user = userDao.selectByPhone(phone);
		//检测用户是否不存在
		Util.checkUserNull(user);

		//设置随机盐
		String salt = Util.randomSalt();
		user.setSalt(salt);
		
		//设置转化后MD5密码
		password = Util.convertPassword(password, salt);
		user.setPassword(password);
		
		//更新密码
		userDao.updatePassword(user);
		
		Password p = new Password();
		p.setPassword(password);
		p.setSalt(salt);
		p.setUserId(user.getId());
		passwordDao.insertNewPassword(p);
		
		
		return Util.genUserMap(user);
	}
	
	public Map<String, Object> register(
			String phone,
			String nickname,
			String password,
			int codeId,
			int code
			) throws StatusException{
		//验证code
		captchaService.verify(codeId, code, phone);
		
		
		User user = userDao.selectByPhone(phone);
		//检测用户是否已存在
		Util.checkUserExist(user);
		
		user = new User();
		user.setPhone(phone);
		
		//设置随机盐
		String salt = Util.randomSalt();
		user.setSalt(salt);
		
		//设置转化后MD5密码
		password = Util.convertPassword(password, salt);
		user.setPassword(password);
		
		//设置token
		//String token = randomToken();
		user.setTokenId(0);
		
		//设置昵称
		user.setNickname(nickname);
		
		userDao.insertNewUser(user);
		
		Password p = new Password();
		p.setPassword(password);
		p.setSalt(salt);
		p.setUserId(user.getId());
		passwordDao.insertNewPassword(p);
		
		return Util.genUserMap(user);
	}
	
	
	public static class Util{
		public static String randomToken(){
			String token = UUID.randomUUID().toString().substring(0, 18).toUpperCase().replaceAll("-", "");
			return token;
		}
		public static String randomSalt(){
			String salt = UUID.randomUUID().toString().substring(9, 18).toUpperCase().replaceAll("-", "");
			return salt;
		}
		
		/**
		 * 检测用户是否不存在
		 * @param user
		 * @throws StateException
		 */
		public static void checkUserNull(User user) throws StatusException {
			if(user==null)throw new StatusException(StatusCode.USER_NULL);
			
		}
		
		/**
		 * 检测用户是否已存在
		 * @param user
		 * @throws StateException
		 */
		public static void checkUserExist(User user) throws StatusException {
			if(user!=null)throw new StatusException(StatusCode.USER_EXIST);
			
		}
		
		public static String convertPassword(String password,String salt){
			return SecurityFactory.getCodeMothod("MD5").encode(password + salt);
		}

		public static boolean checkPassword(User user,String password) throws StatusException{
			String result = SecurityFactory.getCodeMothod("MD5").encode(password+user.getSalt());
			return checkPassword(result,user.getPassword());
		}
		
		/**
		 * 检测密码正确性
		 * @param pwd1
		 * @param pwd2
		 * @return 
		 * @throws StateException
		 */
		public static boolean checkPassword(String pwd1, String pwd2) throws StatusException {
			if(!pwd1.equals(pwd2))throw new StatusException(StatusCode.PASSWORD_ERROR);
			return true;
		}
		
		public static Map<String, Object> genUserMap(User user){
			Map<String, Object> item = new HashMap<>();
			item.put("id", user.getId());
			item.put("nickname", user.getNickname());
			item.put("phone", convertPhone(user.getPhone()));
			return item;
		}
		
		private static String convertPhone(String phone){
			return phone.substring(0, 3)+"****"+phone.substring(7,11);
		}
		
	}





	
	
	
}
