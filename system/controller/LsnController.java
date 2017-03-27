package com.wemeow.web.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.system.service.LessonService;
import com.wemeow.web.util.Status;
import com.wemeow.web.util.StatusCode;


@Controller
@RequestMapping("lsn")
public class LsnController {

	@Autowired
	private LessonService lsnService;
	
	
	@RequestMapping(path = "vicinity")
	@ResponseBody
	public Status getLsnVicinity(
			@RequestParam String geohash
			){
		try {
			return new Status(true, StatusCode.SUCCESS, lsnService.getPOIVicinity(geohash), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(path = "vicinityT")
	@ResponseBody
	public Status getLsnVicinity(
			@RequestParam double latitude,
			@RequestParam double longitude
			){
		try {
			return new Status(true, StatusCode.SUCCESS, lsnService.getPOIVicinity(latitude,longitude), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(path = "{poiId}/time")
	@ResponseBody
	public Status getLsnAtPoi(
			@PathVariable int poiId,
			@RequestParam int week,
			@RequestParam double time
			){
		try {
			return new Status(true, StatusCode.SUCCESS, lsnService.getRecentLessonList(poiId,week,time), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
