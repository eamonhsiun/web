package com.wemeow.web.user.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int id;
	
	private String nickname;
	
	private String phone;
	
	private int tokenId;
	
	private int passwordId;
	
	private String password;
	
	private String salt;
	
	private boolean isFreeze;
	
	private boolean isAdmin;
	
	private Date createTime;
	
	private String createBy;
	
	private Date updateTime;
	
	private String updateBy;
	
	
}
