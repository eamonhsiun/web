package com.wemeow.web.system.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.system.dao.POIDao;
import com.wemeow.web.system.entity.POI;
import com.ymxiong.open.util.geo.GeoHashUtils;



@Service
public class POIService {
	
	@Autowired
	private POIDao poiDao;
	
	
	public void addPoi(double latitude,double longitude,double altitude,String type,String des){
		POI poi = new POI();
		poi.setLatitude(latitude);
		poi.setLongitude(longitude);
		poi.setAltitude(altitude);
		poi.setType(type);
		poi.setDes(des);
		poiDao.insert(poi);
	}

	public List<POI> getPOIListVicinity(String geohash){
		//TODO：拒绝非法请求
		if(geohash.length()<8)return null;
		List<POI> poiList = poiDao.selectPOIByGeohash(geohash.substring(0,6)+"%");
		return poiList;
	}
	
	public List<Map<String,Object>> getPOIVicinity(double latitude, double longitude) {
		String geohash = GeoHashUtils.encode(latitude, longitude, 16);
		String geohash2 = GeoHashUtils.encode(latitude, longitude, 20);
		System.err.println("===================");
		System.err.println(geohash+" "+geohash2);
		return getPOIVicinity(geohash);
	}

	public List<Map<String,Object>> getPOIVicinity(String geohash) {
		//TODO：拒绝非法请求
		if(geohash.length()<8)return null;
		//double[] latlon = GeoHashUtils.decode(geohash);
		List<Map<String,Object>> poiList = new ArrayList<>();
		poiDao.selectPOIByGeohash(geohash.substring(0,6)+"%").stream().forEach((e)->{
			Map<String,Object> poi = new HashMap<>();
			poi.put("id", e.getId());
			poi.put("latitude", e.getLatitude());
			poi.put("longitude", e.getLongitude());
			poi.put("altitude", e.getAltitude());
			poi.put("type", e.getType());
			poi.put("des", e.getDes());
//			if(e.getType().equals("lesson")){
//				poi.put("extra",lessonService.getRecentlyLesson(e));
//			}else if(e.getType().equals("movie")){
//				poi.put("extra","");
//			}else{
//				poi.put("extra","");
//			}
			poiList.add(poi);
		});
		return poiList;
	}

	public Object modifyPoi(int poiId, double latitude, double longitude) {
		String geohash = GeoHashUtils.encode(latitude, longitude, 20);
		POI poi = new POI();
		poi.setId(poiId);
		poi.setGeohash(geohash);
		poiDao.updateGeohash(poi);
		
		return null;
	}


	
}
