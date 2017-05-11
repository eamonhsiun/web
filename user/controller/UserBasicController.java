package com.wemeow.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.user.service.UserService;
import com.wemeow.web.util.RequestLimit;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;

@Controller
@RequestMapping("user")
public class UserBasicController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "register",method=RequestMethod.GET)
	@ResponseBody
	public Status register(
			@RequestParam String phone,
			@RequestParam String nickname,
			@RequestParam String password,
			@RequestParam int codeId,
			@RequestParam int code
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.register(phone,nickname,password,codeId,code), null);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	@RequestMapping(path = "forget",method=RequestMethod.GET)
	@ResponseBody
	public Status forget(
			@RequestParam String phone,
			@RequestParam String password,
			@RequestParam int codeId,
			@RequestParam int code
			){
		try {
			return new Status(true, StatusCode.SUCCESS, userService.forget(phone,password,codeId,code), null);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	
	
}
