package com.wemeow.web.moment.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moment {

	private int id;
	
	private int userId;
	
	private String content;
	
	private int resendMomentId;
	
	private String resendUrl;
	
	private String images;
	
	private int limitCode; 
	
	private Date createTime;
}
