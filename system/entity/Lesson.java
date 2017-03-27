package com.wemeow.web.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
	private int id;
	private String lessonName;
	private String point;
	private String teacher;
	private String teacherStatus;
	private String department;
	private String type;
	
	//PARSE SOURCE
	private String timeAndPosition;
	//
	
	//NEED TO PARSE!!
	private int week;
	private int startWeek;
	private int endWeek;
	private int alternation;
	private int startTime;
	private int endTime;
	
	private int locationId;
	private String district;
	private String build;
	private int floor;
	private String room;
	//
	
	private String book;
	private int year;
	private String term;
	
	//plan
	private int grade;
	private String status;
	//
	
	//plan AND pub_required
	private String classDes;
	//
	
	//pub_required
	private String remark;
	//
	
	//pub_optional
	private String kind;
	//
}
