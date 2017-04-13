package com.wemeow.web.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.system.service.LessonOfWhuSortService;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;


@Controller
@RequestMapping("sysctr")
public class SysCtrController {

	@Autowired
	private LessonOfWhuSortService lsnSortService;
	
	
	@RequestMapping(path = "sortlsn")
	@ResponseBody
	public Status sortLsn(){
		return new Status(true, StatusCode.SUCCESS, lsnSortService.sortWhuLsn(), null);
	}
	
	@RequestMapping(path = "sortlsnpoi")
	@ResponseBody
	public Status sortLsnPoi(){
		return new Status(true, StatusCode.SUCCESS, lsnSortService.recognizePoiFromLsnWhu(), null);
	}
	
	
	
}
