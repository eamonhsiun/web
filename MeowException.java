package com.wemeow.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeowException extends Exception{
	private static final long serialVersionUID = 3338875521299711142L;

	/**
	 * 日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());
	
	
	
	private Code statusCode;
	
	public MeowException(Code statusCode) {
		this.statusCode=statusCode;
		
		Log.info("\n===============\n"
				+"[MEOW EXCEPTION CODE]:"
				+statusCode
				+"\n===============\n");
	}
	
	public Code getStatusCode(){
		return statusCode;
	}
	
	public enum Code{
		USER_NULL,
		PASSWORD_ERROR,
		DEVICE_CHANGE,
		IP_CHANGE,
		TOKEN_EXPIRED
	}
}
