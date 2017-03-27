package com.wemeow.web.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POI {
	private int id;
	
	private String geohash;
	
	private double latitude;
	
	private double longitude;
	
	private double altitude;
	
	private String type;
	
	private String des;
	
	private Object extra;
}
