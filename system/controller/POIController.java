package com.wemeow.web.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.system.service.POIService;
import com.wemeow.web.util.Status;
import com.wemeow.web.util.StatusCode;


@Controller
@RequestMapping("poi")
public class POIController {

	@Autowired
	private POIService poiService;
	
	@RequestMapping(path = "vicinity")
	@ResponseBody
	public Status getPoiVicinity(
			@RequestParam String geohash
			){
		try {
			return new Status(true, StatusCode.SUCCESS, poiService.getPOIVicinity(geohash), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(path = "vicinityUnSafe")
	@ResponseBody
	public Status getLatLon(
			@RequestParam double latitude,
			@RequestParam double longitude
			){
		try {
			return new Status(true, StatusCode.SUCCESS, poiService.getPOIVicinity(latitude,longitude), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(path = "new")
	@ResponseBody
	public Status newPoi(
			@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double altitude,
			@RequestParam String type,
			@RequestParam String des
			){
		try {
			//return new Status(true, StatusCode.SUCCESS, poiService.getPOIVicinity(geohash), null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(path = "modify")
	@ResponseBody
	public Status modifyPoi(
			@RequestParam int poiId,
			@RequestParam double latitude,
			@RequestParam double longitude
			){
		try {
			return new Status(true, StatusCode.SUCCESS, poiService.modifyPoi(poiId,latitude,longitude), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
