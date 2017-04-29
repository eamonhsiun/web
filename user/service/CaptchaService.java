package com.wemeow.web.user.service;



import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eamon.captcha.MobileCaptcha;
import com.wemeow.web.user.dao.CaptchaDao;
import com.wemeow.web.user.entity.Captcha;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;


@Service
public class CaptchaService {
	
	/**
	 * 最小请求间隔
	 */
	private static final int MIN_INTERVAL = 60000;
	
	
	/**
	 * 最大有效时长
	 */
	private static final int MAX_VALID = 300000;
	
	private static Random random;
	
	static{
		long time = System.currentTimeMillis();
		long seed = time -(long)(10*Math.sin(time));
		random = new Random(seed);
	}

	@Autowired private CaptchaDao captchaDao;
	
	/**
	 * 创建验证码
	 * @param phone
	 * @return
	 * @throws StateException
	 */
	public int create(String phone) throws StatusException{
		Captcha captcha = getCaptchaByPhone(phone);
		//检查创建权限
		checkCreatePermission(captcha);
		//无效化旧验证码
		invalidOldCapthcha(captcha);
		//创建新验证码
		return createNewCaptcha(phone);
	}
	
	

	/**
	 * 创建新验证码
	 * @param phone
	 * @return 验证码id
	 */
	private int createNewCaptcha(String phone) {
		Captcha captcha = new Captcha();
		captcha.setPhone(phone);
		int code = random.nextInt(895678)+104321;
		captcha.setCode(code);
		captchaDao.insertNewCaptcha(captcha);
		sendToUserPhone(phone,code);
		return captcha.getId();
	}



	/**
	 * 发送到用户
	 * @param phone
	 * @param code
	 */
	public void sendToUserPhone(String phone, int code) {
		new Thread(()->{
			MobileCaptcha.sendCaptcha(phone, code+"");
		}).start();
	}



	/**
	 * 无效化旧验证码
	 * @param captcha
	 */
	private void invalidOldCapthcha(Captcha captcha) {
		if(captcha==null)return;
		captchaDao.invalidOldCapthcha(captcha.getId());
	}



	/**
	 * 检查正确性
	 * @param captha
	 * @param code
	 * @return
	 * @throws StateException
	 */
	public boolean check(Captcha captha,int code) throws StatusException {
		Date now = new Date();
		if(now.getTime()-captha.getCreateTime().getTime()>MAX_VALID)throw new StatusException(StatusCode.CAPTCHA_N_VAILD);
		if(captha.getCode()!=code)throw new StatusException(StatusCode.CAPTCHA_ERROR);
		return true;
	}
	
	/**
	 * 检查正确性
	 * @param captha
	 * @param code
	 * @return
	 * @throws StateException
	 */
	public boolean check(int capthaId,int code) throws StatusException {
		Captcha captcha = getCaptchaById(capthaId);
		return check(captcha,code);
	}
	
	/**
	 * 验证验证码
	 * @param captha
	 * @param code
	 * @param phone 
	 * @return
	 * @throws StateException
	 */
	public boolean verify(int capthaId,int code, String phone) throws StatusException {
		Captcha captcha = getCaptchaById(capthaId);
		if(captcha==null)throw new StatusException(StatusCode.CAPTCHA_N_VAILD);
		if(!captcha.getPhone().equals(phone))throw new StatusException(StatusCode.CAPTCHA_N_VAILD);
		boolean result = check(captcha,code);
		if(result){
			new Thread(()->{
				captchaDao.invalidOldCapthcha(capthaId);
			}).start();
		}
		return result;
	}

	
	/**
	 * 检查创建权限
	 * @param captcha
	 * @return
	 * @throws StateException
	 */
	private boolean checkCreatePermission(Captcha captcha) throws StatusException{
		if(captcha==null)return true;
		Date now = new Date();
		if(now.getTime()-captcha.getCreateTime().getTime()<MIN_INTERVAL)throw new StatusException(StatusCode.TOO_FREQUENT);
		//TODO:CHECK IP PERMISSION
		
		return true;
	}
	
	
	/**
	 * Id 获取验证码
	 * @param capthaId
	 * @return
	 */
	private Captcha getCaptchaById(int capthaId) {
		return captchaDao.getCaptchaById(capthaId);
	}
	
	
	/**
	 * phone 获取验证码
	 * @param phone
	 * @return
	 */
	private Captcha getCaptchaByPhone(String phone) {
		return captchaDao.getCaptchaByPhone(phone);
	}
	
	
}
