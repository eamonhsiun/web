package com.wemeow.web.util.state;



public class StatusCode {
	/**
	 * 请求成功
	 */
	public static final int SUCCESS=200;
	/**
	 * 请求失败
	 */
	public static final int FAILED=701;
	/**
	 * 请求过于频繁
	 */
	public static final int TOO_FREQUENT=702;
	/**
	 * 请求失效
	 */
	public static final int INVALID=702;
	
	/**
	 * 参数错误
	 */
	public static final int ERROR_DATA=703;
	
	/**
	 * 用户名错误（用户不存在）
	 */
	public static final int USER_NULL=704;	
	
	/**
	 * 用户名已注册
	 */
	public static final int USER_EXIST=705;	
	
	/**
	 * 验证码过期
	 */
	public static final int CAPTCHA_N_VAILD=706;
	
	/**
	 * 验证码错误
	 */
	public static final int CAPTCHA_ERROR=707;

	/**
	 * 密码错误
	 */
	public static final int PASSWORD_ERROR=708;
	
	/**
	 * 没有权限
	 */
	public static final int PERMISSION_LOW=709;
	/**
	 * 密码太短（6-16）
	 */
	public static final int PASSWORD_LENGTH_ERROR=710;
	/**
	 * 文件上传失败
	 */
	public static final int FILEUPLOAD_ERROR=711;
	/**
	 * 文件过大
	 */
	public static final int FILE_TOO_LARGE=712;
	/**
	 * 不支持的图片格式
	 */
	public static final int UNSUPPORT_IMAGE_FORMAT=713;
	/**
	 * 不支持的文件格式
	 */
	public static final int UNSUPPORT_FILE_FORMAT=714;
	

	/**
	 * 邮箱错误
	 */
	public static final int WRONG_EMAIL=718;
	/**
	 * 邮箱已存在
	 */
	public static final int EMAIL_EXIST=719;
	/**
	 * 邮箱格式错误
	 */
	public static final int WRONG_EMAIL_FORMAT=720;
	/**
	 * 电话格式错误
	 */
	public static final int WRONG_PHONE_FORMAT=721;
	
}