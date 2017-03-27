package com.wemeow.web.system.service;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wemeow.web.system.dao.LessonSortDao;
import com.wemeow.web.system.dao.POIDao;
import com.wemeow.web.system.entity.LessonPreSort;
import com.wemeow.web.system.entity.POI;

@Service
public class LessonOfWhuSortService {
	@Autowired
	private LessonSortDao lessonSortDao;
	
	@Autowired
	private POIDao poiDao;
	
	private Map<String,String> locHash = new HashMap<>();

	private String locRegexString = new String();
	
	public Object sortWhuLsn() {
		initLocToAreaMap();
		List<LessonPreSort> lessonPlan = lessonSortDao.selectLsnPlan();
		List<LessonPreSort> lessonPubRequired = lessonSortDao.selectLsnPubRequired();
		List<LessonPreSort> lessonPubOptional = lessonSortDao.selectLsnPubOptional();
		
		sortLessonPlan(lessonPlan);
		sortLessonPubRequired(lessonPubRequired);
		sortLessonPubOptional(lessonPubOptional);
		
		return null;
	}

	public Object recognizePoiFromLsnWhu(){
		List<LessonPreSort> lessonWhu = lessonSortDao.selectLsnWhu();
		Map<String, LessonPreSort> resultMap = new HashMap<>();
		for(LessonPreSort a:lessonWhu){
			resultMap.put(a.getDistrict()+"-"+a.getBuild(), a);
		}
		Iterator<Entry<String, LessonPreSort>> iter = resultMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry<String, LessonPreSort> entry = iter.next();
			LessonPreSort val = entry.getValue();
			System.out.println(val.getDistrict()+" "+val.getBuild());

			System.out.println(val.getDistrict()+" "+val.getBuild());
			if(val.getDistrict()==null)continue;
			
			POI poi = new POI();
			poi.setType("lesson");
			poi.setAltitude(0.0);
			poi.setDes("武汉大学"+" "+val.getDistrict()+" "+val.getBuild());
			poi.setGeohash("");
			
			poiDao.insert(poi);
			val.setLocationId(poi.getId());
			lessonSortDao.updatePoiId(val);
//			val.setLocationId(location.getId());
//			lessonDao.updateLocationId(val);
		}
		return null;
	}
	
	private void sortLessonPubOptional(List<LessonPreSort> lessonPubOptional) {
		for (LessonPreSort a : lessonPubOptional) {
			 LessonPreSort newLesson = new LessonPreSort();

			if(a.getLessonName()==null||a.getLessonName().equals(" ")){
				a.setLessonName("");
			}
			if(a.getPoint()==null||a.getPoint().equals(" ")){
				a.setPoint("");
			}
			if(a.getTeacher()==null||a.getTeacher().equals(" ")){
				a.setTeacher("");
			}
			if(a.getTeacherStatus()==null||a.getTeacherStatus().equals(" ")){
				a.setTeacherStatus("");
			}
			if(a.getDepartment()==null||a.getDepartment().equals(" ")){
				a.setDepartment("");
			}
			if(a.getTimeAndPosition()==null||a.getTimeAndPosition().equals(" ")||a.getTimeAndPosition().equals("NULL")){
				a.setTimeAndPosition("");
			}
			if(a.getRemark()==null||a.getRemark().equals(" ")){
				a.setRemark("");
			}
			if(a.getClassDes()==null||a.getClassDes().equals("NULL")||a.getClassDes().equals(" ")){
				a.setClassDes("");
			}
			if(a.getBook()==null||a.getBook().equals("NULL")||a.getBook().equals(" ")){
				a.setBook("");
			}
			
			newLesson.setLessonName(a.getLessonName());
			newLesson.setPoint(a.getPoint());
			newLesson.setTeacher(a.getTeacher());
			newLesson.setTeacherStatus(a.getTeacherStatus());
			newLesson.setDepartment(a.getDepartment());
			newLesson.setType("公共选修");
			newLesson.setTimeAndPosition(a.getTimeAndPosition());
			parseWhuData(newLesson);			
			//System.out.println(a.getBook());
			newLesson.setClassDes(
					a.getYear() + " " + a.getTerm() + " " + a.getClassDes() + " " + a.getBook());
			try{
				lessonSortDao.insertNewLesson(newLesson);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println(newLesson);
			}
			//System.out.println(newLesson.getClassDes());
		}
	}



	private void sortLessonPubRequired(List<LessonPreSort> lessonPubRequired) {
		for (LessonPreSort a : lessonPubRequired) {
			LessonPreSort newLesson = new LessonPreSort();
			if(a.getLessonName()==null||a.getLessonName().equals(" ")){
				a.setLessonName("");
			}
			if(a.getPoint()==null||a.getPoint().equals(" ")){
				a.setPoint("");
			}
			if(a.getTeacher()==null||a.getTeacher().equals(" ")){
				a.setTeacher("");
			}
			if(a.getTeacherStatus()==null||a.getTeacherStatus().equals(" ")){
				a.setTeacherStatus("");
			}
			if(a.getDepartment()==null||a.getDepartment().equals(" ")){
				a.setDepartment("");
			}
			if(a.getTimeAndPosition()==null||a.getTimeAndPosition().equals(" ")||a.getTimeAndPosition().equals("NULL")){
				a.setTimeAndPosition("");
			}
			if(a.getRemark()==null||a.getRemark().equals(" ")){
				a.setRemark("");
			}
			if(a.getClassDes()==null||a.getClassDes().equals("NULL")||a.getClassDes().equals(" ")){
				a.setClassDes("");
			}
			if(a.getBook()==null||a.getBook().equals("NULL")||a.getBook().equals(" ")){
				a.setBook("");
			}
			
			newLesson.setLessonName(a.getLessonName());
			newLesson.setPoint(a.getPoint());
			newLesson.setTeacher(a.getTeacher());
			newLesson.setTeacherStatus(a.getTeacherStatus());
			newLesson.setDepartment(a.getDepartment());
			newLesson.setType("公共必修");
			newLesson.setRemark(a.getRemark());
			newLesson.setTimeAndPosition(a.getTimeAndPosition());
			parseWhuData(newLesson);

			newLesson.setClassDes(
					a.getYear() + " " + a.getTerm() + " " + a.getClassDes() + " " + a.getBook());
			try{
				lessonSortDao.insertNewLesson(newLesson);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println(newLesson);
			}
			//System.out.println(newLesson.getClassDes());
		}
	}

	private void sortLessonPlan(List<LessonPreSort> lessonPlan) {
		LessonPreSort newLesson;
		for (LessonPreSort a : lessonPlan) {
			newLesson = new LessonPreSort();

			if(a.getLessonName()==null||a.getLessonName().equals(" ")){
				a.setLessonName("");
			}
			if(a.getPoint()==null||a.getPoint().equals(" ")){
				a.setPoint("");
			}
			if(a.getTeacher()==null||a.getTeacher().equals(" ")){
				a.setTeacher("");
			}
			if(a.getTeacherStatus()==null||a.getTeacherStatus().equals(" ")){
				a.setTeacherStatus("");
			}
			if(a.getDepartment()==null||a.getDepartment().equals(" ")){
				a.setDepartment("");
			}
			if(a.getTimeAndPosition()==null||a.getTimeAndPosition().equals(" ")||a.getTimeAndPosition().equals("NULL")){
				a.setTimeAndPosition("");
			}
			if(a.getRemark()==null||a.getRemark().equals(" ")){
				a.setRemark("");
			}
			if(a.getClassDes()==null||a.getClassDes().equals("NULL")||a.getClassDes().equals(" ")){
				a.setClassDes("");
			}
			if(a.getBook()==null||a.getBook().equals("NULL")||a.getBook().equals(" ")){
				a.setBook("");
			}
			
			newLesson.setLessonName(a.getLessonName());
			newLesson.setPoint(a.getPoint());
			newLesson.setTeacher(a.getTeacher());
			newLesson.setTeacherStatus(a.getTeacherStatus());
			newLesson.setDepartment(a.getDepartment());
			newLesson.setType(a.getStatus());
			newLesson.setTimeAndPosition(a.getTimeAndPosition());
			
			parseWhuData(newLesson);
			newLesson.setClassDes(
					a.getYear() + " " + a.getTerm() + " " + a.getGrade() + "级" + a.getClassDes() + " " + a.getBook());
			//System.out.println(newLesson.getClassDes());
			try{
				lessonSortDao.insertNewLesson(newLesson);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println(newLesson);
			}
		}
	}


	private void parseWhuData(LessonPreSort lesson) {
		//String source = "星期二:1-18周,每1周;6-7节,1区,01101";
		
		//String source = "星期二:1-18周,每1周;6-7节,1区,枫-214";
		
		//String source = "星期二:1-18周,每1周;6-7节,1区,新外-14";
		
		//String source = "星期二:1-18周,每1周;6-7节,1区,新外-语音04";
		
		String source = lesson.getTimeAndPosition();

		
		//解析 周二，星期二 
		String week= source.replaceAll("(^星期)|(^周)|(:.*)", "");
		if(week.equals(""))week="0";
		
		//解析 A-B周 
		String weekLast = source.replaceAll("(^.*:)|周.*", "");
		if(weekLast.equals(""))weekLast="0";
		//解析 A
		String startWeek = weekLast.replaceFirst("-.*$", "");
		if(startWeek.equals(""))startWeek="0";
		//解析 B
		String endWeek = weekLast.replaceFirst("^.*-", "");
		if(endWeek.equals(""))endWeek="0";
		//解析 每 A周
		String alternation = source.replaceAll("(^.*每)|(周.*)", "");
		if(alternation.equals(""))alternation="0";
		//解析 C-D节
		String time = source.replaceAll("(^.*周;)|(节.*)|(^.*周)", "");
		
		//解析 C
		String startTime = time.replaceFirst("-.*$", "");
		if(startTime.equals(""))startTime="0";
		//解析 D
		String endTime = time.replaceFirst("^.*-", "");
		if(endTime.equals(""))endTime="0";
		
		String importantResult = source.replaceAll("(^.*节;)|(^.*节,)|(^.*节)", "");	
		
		//解析 1区 国软 等
		String district = importantResult.replaceAll(",.*$", "");
		
		String details = importantResult.replaceAll("^.*,", "");
		
		String build = details.replaceAll("(-.*$)|(\\d{3};$)|(\\d{3}$)", "").replaceAll(";", "");
		
		String room = details.replaceFirst(build, "").replaceAll("-", "").replaceAll(";", "");
		
		String floor="";
		
		if(room.length()>2){
			Pattern p = Pattern.compile("(^\\d{1})");
			Matcher m = p.matcher(room);
			while (m.find()) {
				floor = m.group();
			}
		}
		if(floor.equals("")){
			floor="0";
		}
		

		//TODO:
		//System.out.println(district+ " " + build +" "+ room+ " "+floor);
		
		lesson.setWeek(recognizeWeek(week));
		lesson.setStartWeek(Integer.parseInt(startWeek));
		lesson.setEndWeek(Integer.parseInt(endWeek));
		lesson.setAlternation(Integer.parseInt(alternation));
		lesson.setStartTime(Integer.parseInt(startTime));
		lesson.setEndTime(Integer.parseInt(endTime));
		lesson.setDistrict(district);
		lesson.setBuild(build);
		lesson.setRoom(room);
		lesson.setFloor(Integer.parseInt(floor));
		if(district.equals("")&&lesson.getDepartment().equals("体育部")){
			lesson.setClassDes(lesson.getRemark());
			recognizePEPlace(lesson);
			
		}
		

		//TODO:
		//System.out.print(lesson.getTimeAndPosition());
		
		
//		System.out.println("  星期"+lesson.getWeek()
//							+" "+lesson.getStartWeek()
//							+"-"+lesson.getEndWeek()
//							+"周 每"+lesson.getAlternation()
//							+"周 "+lesson.getStartTime()
//							+"-"+lesson.getEndTime()
//							+" "+lesson.getDistrict()
//							+" "+lesson.getBuild()
//							+"-"+lesson.getRoom()
//				
//				
//				);

	}

	private void recognizePEPlace(LessonPreSort lesson) {
		String source = lesson.getRemark();
		if(source==null||source.equals(""))return;
		
		
		Pattern p = Pattern.compile(locRegexString);
		Matcher m = p.matcher(source);
		while (m.find()) {
			String build = m.group();
			lesson.setBuild(build);
//			System.err.println("===");
//			System.err.println(build+" "+locHash.get(build));
//			System.err.println("===");
			lesson.setDistrict(locHash.get(build));
		}
	}

	private int recognizeWeek(String week) {
		if (week.equals("一")) {
			return 1;
		} else if (week.equals("二")) {
			return 2;
		} else if (week.equals("三")) {
			return 3;
		} else if (week.equals("四")) {
			return 4;
		} else if (week.equals("五")) {
			return 5;
		} else if (week.equals("六")) {
			return 6;
		} else if (week.equals("0")){
			return 0;
		} else{
			return 7;
		}

	}
	

	
	private void initLocToAreaMap() {
		locHash.put("宋卿", "1区");
		locHash.put("工学部排球场", "2区");
		locHash.put("信息学部网球场", "3区");
		locHash.put("工学部田径场", "2区");
		locHash.put("医学部田径场", "4区");
		locHash.put("工学部篮球场", "2区");
		locHash.put("梅园", "1区");
		locHash.put("桂园", "1区");
		locHash.put("医学部网球场", "4区");
		locHash.put("信息学部田径场", "3区");
		locHash.put("信息学部体育馆", "3区");
		locHash.put("医学部体育馆", "4区");
		locHash.put("信息学部田径场", "3区");
		locHash.put("工学部体育馆", "2区");
		locHash.put("医学部门口篮球场", "4区");
		locHash.put("信息学部游泳池旁的羽毛球场", "3区");
		locHash.put("奥场", "1区");
		locHash.put("风雨", "1区");
		locHash.put("工学部网球场", "2区");

		Iterator<Entry<String, String>> iter = locHash.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			locRegexString+= key;
			if(iter.hasNext())locRegexString+="|";
		}
		System.out.println(locRegexString);
	}

	public static void main(String[] args) {
		LessonOfWhuSortService loss = new LessonOfWhuSortService();
		loss.initLocToAreaMap();
	}
	
}
