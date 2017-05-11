package com.wemeow.web.user.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
	private int id;
	
	private String nickname;
	
	private String phone;
	
	private int tokenId;
	
	private String authc;
	
	private String password;
	
	private String salt;
	
	private boolean isFreeze;
	
	private String role;
	
	private Date createTime;
	
	private String createBy;
	
	private Date updateTime;
	
	private String updateBy;
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User))return false;
		return ((User)obj).getId()==this.id;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
