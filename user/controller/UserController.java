package com.wemeow.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.user.service.UserService;
import com.wemeow.web.util.Status;
import com.wemeow.web.util.StatusCode;


@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "register")
	@ResponseBody
	public Status register(
			@RequestParam String phone,
			@RequestParam String nickname,
			@RequestParam String pwd,
			@RequestParam int vCodeId,
			@RequestParam int vCode
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.register(phone,nickname,pwd,vCodeId,vCode), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@RequestMapping(path = "{userId}/friend")
	@ResponseBody
	public Status getFriend(
			@PathVariable int userId
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.getUserFriends(userId), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(path = "friend/add")
	@ResponseBody
	public Status createFriend(
			@RequestParam int userId,
			@RequestParam int followId
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.insertFollower(userId, followId), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
