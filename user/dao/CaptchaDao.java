package com.wemeow.web.user.dao;

import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.Captcha;

@Repository
public interface CaptchaDao {
	Captcha getCodeById(int id);
	int insertNewCaptcha(Captcha captcha);
}
