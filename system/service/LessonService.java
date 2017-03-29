package com.wemeow.web.system.service;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.system.dao.LessonDao;
import com.wemeow.web.system.dao.POIDao;
import com.wemeow.web.system.entity.Lesson;
import com.wemeow.web.system.entity.POI;
import com.ymxiong.open.util.geo.GeoHashUtils;



@Service
public class LessonService {

	@Autowired
	private LessonDao lessonDao;
	
	@Autowired
	private POIDao poiDao;
	
	public List<Map<String,Object>> getPOIVicinity(double latitude, double longitude) {
		String geohash = GeoHashUtils.encode(latitude, longitude, 20);
		System.err.println(geohash);
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
			double[] latlon = GeoHashUtils.decode(e.getGeohash());
			poi.put("latitude", latlon[0]);
			poi.put("longitude", latlon[1]);
			poi.put("altitude", e.getAltitude());
			poi.put("type", e.getType());
			poi.put("des", e.getDes());
			if(e.getType().equals("lesson")){
				poi.put("extra",getRecentlyLesson(e.getId()));
				poiList.add(poi);
			}
		});
		return poiList;
	}
	
	/**
	 * 获取即将上的课程或者正在上的课程
	 * @param poi
	 * @return
	 */
	public List<Map<String,Object>> getRecentlyLesson(int poiId){
		List<Map<String,Object>> lessonList = new ArrayList<>();
		getRecentLessonList(poiId).stream().forEach((e)->{
			lessonList.add(getLsnItem(e));
		});
		return lessonList;
	}
	
	/**
	 * 获取所有课程
	 * @param poi
	 * @return
	 */
	public List<Map<String,Object>> getLesson(POI poi){
		List<Map<String,Object>> lessonList = new ArrayList<>();
		lessonDao.selectLsnRecentlyAtPoi(poi.getId()).stream().forEach((e)->{
			lessonList.add(getLsnItem(e));
		});
		return lessonList;
	}

	
	/**
	 * 获取单个课程数据
	 * @param e
	 * @return
	 */
	private Map<String, Object> getLsnItem(Lesson e) {
		Map<String,Object> item = new HashMap<>();
		item.put("id", e.getId());
		item.put("lessonName", e.getLessonName());
		item.put("point", e.getPoint());
		item.put("teacher", e.getTeacher());
		item.put("teacherStatus", e.getTeacherStatus());
		item.put("department", e.getDepartment());
		item.put("type", e.getType());
		item.put("classDes", e.getClassDes());
		item.put("week", e.getWeek());
		item.put("startWeek", e.getStartWeek());
		item.put("endWeek", e.getEndWeek());
		item.put("alternation", e.getAlternation());
		item.put("startTime", e.getStartTime());
		item.put("endTime", e.getEndTime());
		item.put("district", e.getDistrict());
		item.put("build", e.getBuild());
		item.put("floor", e.getFloor());
		item.put("room", e.getRoom());
		return item;
	}
	
	public List<Lesson> getRecentLessonList(int poiId){
		Calendar now = Calendar.getInstance();
		int week = now.get(Calendar.DAY_OF_WEEK);
		week=week-1;
		if(week==0)week=7;
		double time = getWhuClassNow(now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE));
		return getRecentLessonList(poiId,week,time);
	}
	
	public List<Lesson> getRecentLessonList(int poiId,int week,double time){
		List<Lesson> list = lessonDao.selectRecentLesson(poiId,week,time);
		return list;
	}

	
//
//	private String recognizeStartTime(int i) {
//		switch(i){
//		case 1:
//			return "08:00";
//		case 2:
//			return "08:50";
//		case 3:
//			return "09:50";
//		case 4:
//			return "10:40";
//		case 5:
//			return "11:30";
//		case 6:
//			return "14:05";
//		case 7:
//			return "14:55";
//		case 8:
//			return "15:45";
//		case 9:
//			return "16:40";
//		case 10:
//			return "17:30";		
//		case 11:
//			return "18:30";		
//		case 12:
//			return "19:20";		
//		case 13:
//			return "20:10";		
//		
//		}
//		return "";
//	}


	private static double getWhuClassNow(int hour,int minute){
		switch(hour){
		case 8:
			if(minute<=45)return 1;
			else if(minute>=50)return 2;
			else return 1.5;
		case 9:
			if(minute<=35)return 2;
			else if(minute>=50)return 3;
			else return 2.5;
		case 10:
			if(minute<=35)return 3;
			else if(minute>=40) return 4;
			else return 3.5;
		case 11:
			if(minute<=25)return 4;
			else if(minute>=30)return 5;
			else return 4.5;
		case 12:
			if(minute<=15)return 5;
			else return 5.5;
		case 13:
			return 5.5;
		case 14:
			if(minute<5)return 5.5;
			else if((minute>=5)&&(minute<=50))return 6;
			else if((minute>50)&&(minute<55))return 6.5;
			else return 7;
		case 15:
			if(minute<=40)return 7;
			else if(minute>=45)return 8;
			else return 7.5;
		case 16:
			if(minute<=30)return 8;
			else if(minute>=40)return 9;
			else return 8.5;
		case 17:
			if(minute<=25)return 9;
			else if(minute>=40)return 10;
			else return 9.5;
		case 18:
			if(minute<=25)return 10;
			else if(minute>=30)return 11;
			else return 10.5;
		case 19:
			if(minute<=15)return 11;
			else if(minute>=20)return 12;
			else return 11.5;
		case 20:
			if(minute<=5)return 12;
			else if(minute>=10 && minute<=55)return 13;
			else if(minute>5 && minute<10)return 12.5;
		default:
			return 0;
		}
	}
	
	
	
//	
//	private String recognizeEndTime(int i) {
//		switch(i){
//		case 1:
//			return "08:45";
//		case 2:
//			return "09:35";
//		case 3:
//			return "10:35";
//		case 4:
//			return "11:25";
//		case 5:
//			return "12:15";
//		case 6:
//			return "14:50";
//		case 7:
//			return "15:40";
//		case 8:
//			return "16:30";
//		case 9:
//			return "17:25";
//		case 10:
//			return "18:15";		
//		case 11:
//			return "19:15";		
//		case 12:
//			return "20:05";		
//		case 13:
//			return "20:55";		
//		
//		}
//		return "";
//	}
//



}
