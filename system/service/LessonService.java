package com.wemeow.web.system.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.system.dao.LessonDao;
import com.wemeow.web.system.entity.Lesson;
import com.wemeow.web.system.entity.POI;



@Service
public class LessonService {

	@Autowired
	private LessonDao lessonDao;
	
	
	/**
	 * 获取即将上的课程或者正在上的课程
	 * @param poi
	 * @return
	 */
	public List<Map<String,Object>> getRecentlyLesson(POI poi){
		List<Map<String,Object>> lessonList = new ArrayList<>();
		lessonDao.selectLsnRecentlyAtPoi(poi.getId()).stream().forEach((e)->{
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


}
