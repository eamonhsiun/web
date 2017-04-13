package com.wemeow.web.user.dao;

import org.springframework.stereotype.Repository;

import com.wemeow.web.user.entity.Captcha;

@Repository
public interface CaptchaDao {
	int invalidOldCapthcha(int id);

	Captcha getCaptchaById(int capthaId);

	Captcha getCaptchaByPhone(String phone);

	int insertNewCaptcha(Captcha captcha);

}
