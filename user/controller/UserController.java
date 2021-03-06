package com.wemeow.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.user.service.UserService;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;


@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@RequestMapping(path = "{userId}/friend")
	@ResponseBody
	public Status getFriend(
			@PathVariable int userId
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.getUserFriends(userId), null);
		} catch (Exception e) {
			return StatusException.procExcp(e);
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
			return StatusException.procExcp(e);
		}
	}
	
	
	
	
}
