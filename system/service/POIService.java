package com.wemeow.web.system.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.system.dao.POIDao;
import com.wemeow.web.system.entity.POI;



@Service
public class POIService {
	
	@Autowired
	private POIDao poiDao;
	
	@Autowired
	private LessonService lessonService;

	
	public List<Map<String,Object>> getPOINearBy(double latitude,double longitude){
		List<Map<String,Object>> poiList = new ArrayList<>();
		poiDao.selectPOI().stream().forEach((e)->{
			Map<String,Object> poi = new HashMap<>();
			poi.put("id", e.getId());
			poi.put("latitude", e.getLatitude());
			poi.put("longitude", e.getLongitude());
			poi.put("altitude", e.getAltitude());
			poi.put("type", e.getType());
			poi.put("desc", e.getDesc());
			if(e.getType().equals("lesson")){
				poi.put("extra",lessonService.getRecentlyLesson(e));
			}else if(e.getType().equals("movie")){
				poi.put("extra","");
			}else{
				poi.put("extra","");
			}
			poiList.add(poi);
		});
		return poiList;
	}
	
	public void addPoi(double latitude,double longitude,double altitude,String type,String desc){
		POI poi = new POI();
		poi.setLatitude(latitude);
		poi.setLongitude(longitude);
		poi.setAltitude(altitude);
		poi.setType(type);
		poi.setDesc(desc);
		poiDao.insert(poi);
	}


	public Object getPOIVicinity(String geohash) {
		
		
		return null;
	}
	
	
	
	

}
