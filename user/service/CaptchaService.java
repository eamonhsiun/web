package com.wemeow.web.user.service;



import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.user.dao.CaptchaDao;
import com.wemeow.web.user.entity.Captcha;


@Service
public class CaptchaService {
	
	
	@Autowired
	private CaptchaDao captchaDao;
	
	private static Random random;
	static{
		long time = System.currentTimeMillis();
		long seed = time -(long)(10*Math.sin(time));
		random = new Random(seed);
	}
	
	
	public Captcha genNewCaptcha(String phone){
		Captcha captcha = new Captcha();
		captcha.setPhone(phone);
		captcha.setVCode(random.nextInt(899699)+100300);
		captchaDao.insertNewCaptcha(captcha);
		return captcha;
		
	}
	
	
	public Captcha getCaptchaById(int id){
		return captchaDao.getCodeById(id);
	}
	
	
	
}
