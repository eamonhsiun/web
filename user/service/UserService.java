package com.wemeow.web.user.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.PasswordDao;
import com.wemeow.web.user.dao.UserDao;
import com.wemeow.web.user.entity.Password;
import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;
import com.ymxiong.open.util.security.SecurityFactory;


@Service
public class UserService {
	
	/**
	 * 最大有效时长
	 */
	private static final long MAX_VALID_TOKEN = 1000*60*60*60*24*7;

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


//	public Object register(String phone,String nickname,String pwd, int vCodeId, int vCode) {
//		//TODO:Check Code
//		
//		
//		String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
//		String codePwd = SecurityFactory.getCodeMothod("MD5").encode(pwd+salt);
//		
//		User user = new User();
//		user.setPhone(phone);
//		user.setNickname(nickname);
//		
//		user.setSalt(salt);
//		user.setPassword(codePwd);
//		userDao.insertNewUser(user);
//		
//		Password password = new Password();
//		password.setPassword(codePwd);
//		password.setSalt(salt);
//		password.setUserId(user.getId());
//		passwordDao.insertNewPassword(password);
//
//		return user;
//	}
	
	
	public Map<String, Object> register(
			String phone,
			String nickname,
			String password,
			int codeId,
			int code
			) throws StatusException{
		//验证code
		captchaService.verify(codeId, code);
		
		User user = userDao.selectByPhone(phone);
		//检测用户是否已存在
		checkUserExist(user);
		
		user = new User();
		user.setPhone(phone);
		
		//设置随机盐
		String salt = randomSalt();
		user.setSalt(salt);
		
		//设置转化后MD5密码
		password = convertPassword(password, salt);
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
		
		return genUserMap(user);
	}
	
	/**
	 * 检测用户是否不存在
	 * @param user
	 * @throws StateException
	 */
	private void checkUserNull(User user) throws StatusException {
		if(user==null)throw new StatusException(StatusCode.USER_NULL);
		
	}
	
	
	/**
	 * 检测用户是否已存在
	 * @param user
	 * @throws StateException
	 */
	private void checkUserExist(User user) throws StatusException {
		if(user!=null)throw new StatusException(StatusCode.USER_EXIST);
		
	}


	/**
	 * 检测密码正确性
	 * @param pwd1
	 * @param pwd2
	 * @throws StateException
	 */
	private void checkPassword(String pwd1, String pwd2) throws StatusException {
		if(!pwd1.equals(pwd2))throw new StatusException(StatusCode.PASSWORD_ERROR);
	}
	
	/**
	 * 检测Token
	 * @param user
	 * @param token
	 * @throws StateException
	 */
	private void checkToken(User user, int token) throws StatusException {
		if(user==null)return;
		if(user.getTokenId()==token)throw new StatusException(StatusCode.PERMISSION_LOW);
		Date now = new Date();		
		if((now.getTime()-user.getUpdateTime().getTime())>MAX_VALID_TOKEN){
			user.setTokenId(0);
			userDao.updateTokenId(user);
			throw new StatusException(StatusCode.PERMISSION_LOW);
		}
	}
	
	
	
	
	private String randomSalt(){
		String salt = UUID.randomUUID().toString().substring(9, 18).toUpperCase().replaceAll("-", "");
		return salt;
	}
	
	private static String randomToken(){
		String token = UUID.randomUUID().toString().substring(0, 18).toUpperCase().replaceAll("-", "");
		return token;
	}
	
	
	private String convertPassword(String password,String salt){
		return SecurityFactory.getCodeMothod("MD5").encode(password + salt);
	}
	

	private Map<String, Object> genUserMap(User user){
		Map<String, Object> item = new HashMap<>();
		item.put("id", user.getId());
		item.put("nickname", user.getNickname());
		item.put("phone", user.getPhone());
		item.put("tokenId", user.getTokenId());
		return item;
	}
}
