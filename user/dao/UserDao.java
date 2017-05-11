package com.wemeow.web.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.Token;
import com.wemeow.web.user.entity.User;



@Repository
public interface UserDao {
	User selectById(int id);
	User selectByPhone(String phone);
	
	
	List<User> selectMyFollowerList(int id);
	
	List<User> selectMyFollowList(int id);
	
	int insertFollower(@Param("userId") int userId,@Param("followId") int followId);
	
	int insertNewUser(User user);
	
	//int updateTokenId(User user); 
	int updateToken(Token token);
	
	int updatePassword(User user);
}
