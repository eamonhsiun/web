package com.wemeow.web.user.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Captcha {
	private int id;
	
	private int code;
	
	private String phone;
	
	private Date createTime;
}
