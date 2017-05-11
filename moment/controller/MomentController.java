package com.wemeow.web.moment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wemeow.web.moment.service.MomentService;
import com.wemeow.web.util.state.StatusException;
import com.wemeow.web.util.RequestLimit;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;

@Controller
@RequestMapping("moment")
public class MomentController {

	@Autowired
	private MomentService momentService;
	
		
	@RequestMapping(path = "{userId}",method=RequestMethod.POST)
	@ResponseBody
	public Status postMomentPublic(
			
			
			){
		try {
			return new Status(true, StatusCode.SUCCESS,0,0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	
//	/**
//	 * @param userId
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	@RequestMapping(path = "{userId}/public",method=RequestMethod.GET)
//	@ResponseBody
//	public Status getMomentPublic(
////			@PathVariable int userId,
////			@RequestParam int page,
////			@RequestParam(required=false,defaultValue="10") int rows
//			){
//		try {
//			//return new Status(true, StatusCode.SUCCESS, momentService.viewPublicMoments(userId,page,rows),0);
//			return null;
//		} catch (Exception e) {
//			return StatusException.procExcp(e);
//		}
//	}
	
	
	
	
	/**
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(path = "{userId}/public",method=RequestMethod.GET)
	@ResponseBody
	public Status getMomentPublic(
			@PathVariable int userId,
			@RequestParam int page,
			@RequestParam(required=false,defaultValue="10") int rows
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.viewPublicMoments(userId,page,rows),0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	@RequestLimit(RequestLimit.USER_PRIVATE)
	@RequestMapping(path = "{userId}/public",method=RequestMethod.POST)
	@ResponseBody
	public Status createMomentPublic(
			@PathVariable int userId,
			@RequestParam String content,
			@RequestParam(required=false,defaultValue="0") int resendMomentId,
			@RequestParam(required=false,defaultValue="") String resendUrl,
			@RequestParam(required=false,defaultValue="") String images
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.createPublicMoment(userId,content,resendMomentId,resendUrl,images),0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	/**
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(path = "{userId}/protected",method=RequestMethod.GET)
	@ResponseBody
	public Status getMomentProtected(
			@PathVariable int userId,
			@RequestParam int page,
			@RequestParam(required=false,defaultValue="10") int rows
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.viewProtectedMoments(userId,page,rows),0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	@RequestLimit(RequestLimit.USER_PRIVATE)
	@RequestMapping(path = "{userId}/protected",method=RequestMethod.POST)
	@ResponseBody
	public Status createMomentProtected(
			@PathVariable int userId,
			@RequestParam String content,
			@RequestParam(required=false,defaultValue="0") int resendMomentId,
			@RequestParam(required=false,defaultValue="") String resendUrl,
			@RequestParam(required=false,defaultValue="") String images
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.createProtectedMoment(userId,content,resendMomentId,resendUrl,images),0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	
	/**
	 * 浏览该用户可浏览的所有动态
	 * @return
	 */
	@RequestLimit(RequestLimit.USER_PRIVATE)
	@RequestMapping(path = "{userId}/friend",method=RequestMethod.GET)
	@ResponseBody
	public Status getAllMoment(
			@PathVariable int userId,
			@RequestParam int page,
			@RequestParam(required=false,defaultValue="10") int rows
			){
		try {
			return new Status(true, StatusCode.SUCCESS, momentService.viewFriendMoments(userId, page, rows),0);
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}
	
	
	
}




