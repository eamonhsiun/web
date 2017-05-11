package com.wemeow.web.meow.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meow {
	
	private int id;
	
	private int userId;
	
	private double latitude;
	
	private double longitude;
	
	private double altitude;
	
	private String geohash;
	
	private String content;
	
	private int resentMeowId;
	
	private String images;
	
	private Date createTime;
	
	private boolean isDelete;
	
}
