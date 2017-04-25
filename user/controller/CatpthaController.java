package com.wemeow.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.user.service.CaptchaService;
import com.wemeow.web.util.RequestLimit;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;

@RequestLimit(RequestLimit.USER_PUBLIC)
@Controller
@RequestMapping("captcha")
public class CatpthaController {

	@Autowired
	private CaptchaService captchaService;
	
	@RequestMapping(path = "")
	@ResponseBody
	public Status create(
			@RequestParam String phone
			){
		try {
			return new Status(true, StatusCode.SUCCESS, captchaService.create(phone), null);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	
}
