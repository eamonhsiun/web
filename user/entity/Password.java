package com.wemeow.web.user.entity;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
	
	private int id;
	
	private int userId;
	
	private String password;
	
	private String salt;
	
	private int ipLong;
	
	private Date createTime;
	
}
