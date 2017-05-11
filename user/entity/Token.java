package com.wemeow.web.user.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
	private int id;
	
	private int userId;
	
	private long ipLong;
	
	private String IMEI;
	
	private String authc;
	
	private Date createTime;
	
	private String createBy;
	
}
