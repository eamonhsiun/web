package com.wemeow.web.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POI {
	private int id;
	
	private double latitude;
	
	private double longitude;
	
	private double altitude;
	
	private String type;
	
	private String desc;
	
	private Object extra;
}
