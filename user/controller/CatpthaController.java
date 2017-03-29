package com.wemeow.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.user.entity.Captcha;
import com.wemeow.web.user.service.CaptchaService;
import com.wemeow.web.util.Status;
import com.wemeow.web.util.StatusCode;

@Controller
@RequestMapping("captcha")
public class CatpthaController {

	@Autowired
	private CaptchaService captchaService;
	
	@RequestMapping(path = "new")
	@ResponseBody
	public Status newCaptha(
			@RequestParam String phone
			){
		try {
			Captcha captcha =captchaService.genNewCaptcha(phone);
			return new Status(true, StatusCode.SUCCESS, captcha.getId(), captcha.getVCode());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(path = "test")
	@ResponseBody
	public Status getCaptchaById(
			@RequestParam int id
			){
		try {
			Captcha captcha =captchaService.getCaptchaById(id);
			return new Status(true, StatusCode.SUCCESS, captcha.getId(), captcha.getVCode());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
