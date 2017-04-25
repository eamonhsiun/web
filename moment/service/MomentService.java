package com.wemeow.web.moment.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.moment.dao.MomentDao;
import com.wemeow.web.moment.entity.Moment;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;



@Service
public class MomentService {
	
	@Autowired
	private MomentDao momentDao;
	

	/**
	 * 生成一个新的moment
	 * @param userId
	 * @param content
	 * @param resendMomentId
	 * @param resendUrl
	 * @param images
	 * @return
	 */
	public Object createPublicMoment(int userId, String content, int resendMomentId, String resendUrl, String images) {
		Moment moment = new Moment();
		
		moment.setUserId(userId);
		moment.setContent(content);
		moment.setResendMomentId(resendMomentId);
		moment.setResendUrl(resendUrl);
		moment.setImages(images);
		
		int i = momentDao.insertNewMoment(moment);
		System.out.println(i);
		return null;
	}
	
	/**
	 * 生成一个新的moment
	 * @param userId
	 * @param content
	 * @param resendMomentId
	 * @param resendUrl
	 * @param images
	 * @return
	 */
	public Object createProtectedMoment(int userId, String content, int resendMomentId, String resendUrl, String images) {
		Moment moment = new Moment();
		
		moment.setUserId(userId);
		moment.setContent(content);
		moment.setResendMomentId(resendMomentId);
		moment.setResendUrl(resendUrl);
		moment.setImages(images);
		
		int i = momentDao.insertNewMoment(moment);
		System.out.println(i);
		return null;
	}
	
	/**
	 * 浏览公开moment
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 * @throws StatusException
	 */
	public List<Map<String, Object>> viewPublicMoments(int userId, int page, int rows) throws StatusException {
		List<Map<String, Object>> list = new ArrayList<>();
		if(page<1)throw new StatusException(StatusCode.PARA_ERROR);
		int start = (page-1)*rows;
		momentDao.selectPublicMoments(userId,start,rows).stream().forEach((e)->{
			list.add(genMomentMap(e));
		});
		return list;
	}
	
	
	/**
	 * 浏览受保护的moment，指定人浏览
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 * @throws StatusException 
	 */
	public List<Map<String, Object>> viewProtectedMoments(int userId, int page, int rows) throws StatusException {
		List<Map<String, Object>> list = new ArrayList<>();
		if(page<1)throw new StatusException(StatusCode.PARA_ERROR);
		int start = (page-1)*rows;
		momentDao.selectAllMoments(userId,start,rows).stream().forEach((e)->{
			
			
		});
		return list;
	}
	
	
	/**
	 * 浏览该用户所有朋友的moment
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 * @throws StatusException 
	 */
	public List<Map<String, Object>> viewFriendMoments(int userId, int page, int rows) throws StatusException {
		List<Map<String, Object>> list = new ArrayList<>();
		if(page<1)throw new StatusException(StatusCode.PARA_ERROR);
		int start = (page-1)*rows;
		momentDao.selectFriendMoments(userId,start,rows).stream().forEach((e)->{
			list.add(genMomentMap(e));
		});
		return list;
	}
	
	/**
	 * 根据id浏览
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	public Map<String, Object> viewMomentById(int momentId) throws StatusException {
		Moment e = momentDao.selectMoment(momentId);
		if(e==null)throw new StatusException(StatusCode.FAILED);
		return genMomentMap(e);
	}
	
	
	/**
	 * 生成moment map
	 * @param e
	 * @return
	 */
	private Map<String, Object> genMomentMap(Moment e){
		Map<String, Object> item = new HashMap<>();
		item.put("id", e.getId());
		item.put("content", e.getContent());
		item.put("userId", e.getUserId());
		item.put("images", e.getImages());
		item.put("resendMoment", e.getResendMomentId());
		item.put("resendUrl", e.getResendUrl());
		item.put("createTime", e.getCreateTime());
		return item;
	}
	
	
	
	
}
