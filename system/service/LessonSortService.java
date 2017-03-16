package com.wemeow.web.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wemeow.web.system.dao.LessonDao;
import com.wemeow.web.system.entity.Lesson;

public class LessonSortService {
	@Autowired
	private LessonDao lessonDao;
	
	
	public List<Map<String,Object>> getLessonByLocation(int locationId,int week){
		List<Map<String,Object>> result = new ArrayList<>();
		
		List<Lesson> lessons = lessonDao.selectByLocation(locationId, week);
		
		lessons.forEach((lesson)->{
			result.add(genLessonMap(lesson));
		});
		
		return result;
	}
	
	
	
	private Map<String, Object> genLessonMap(Lesson e){
		Map<String, Object> item = new HashMap<>();
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
