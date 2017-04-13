package com.wemeow.web.moment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.moment.service.MomentService;
import com.wemeow.web.util.state.StatusException;
import com.wemeow.web.util.RequestLimit;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;

@RequestLimit(RequestLimit.USER_PRIVATE)
@Controller
@RequestMapping("moment")
public class MomentController {

	@Autowired
	private MomentService momentService;
	
	
	@RequestMapping(path = "{userId}",method=RequestMethod.POST)
	@ResponseBody
	public Status createMoment(
			@PathVariable int userId,
			@RequestParam String content,
			@RequestParam String extra
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.moment(),0);
		} catch (Exception e) {
			e.printStackTrace();
			return StatusException.procExcp(e);
		}
	}
	
	@RequestMapping(path = "{userId}",method=RequestMethod.GET)
	@ResponseBody
	public Status getMoment(
			
			){

		return new Status(true, StatusCode.SUCCESS, momentService.moment(),0);
	}
	
	
	@RequestMapping(path = "friend",method=RequestMethod.GET)
	@ResponseBody
	public Status getAllMoment(
			
			
			){

		return new Status(true, StatusCode.SUCCESS, momentService.moment(),0);
	}
	
	
	
	
	
//	@RequestMapping(path = "new")
//	@ResponseBody
//	public Status newCaptha(
//			@RequestParam String phone
//			){
//		try {
//			Captcha captcha =captchaService.genNewCaptcha(phone);
//			return new Status(true, StatusCode.SUCCESS, captcha.getId(), captcha.getVCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	@RequestMapping(path = "test")
//	@ResponseBody
//	public Status getCaptchaById(
//			@RequestParam int id
//			){
//		try {
//			Captcha captcha =captchaService.getCaptchaById(id);
//			return new Status(true, StatusCode.SUCCESS, captcha.getId(), captcha.getVCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	
	
}
