package com.wemeow.web.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonPreSort {
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

	@Override
	public String toString() {
		return "LessonPreSort [id=" + id + ", lessonName=" + lessonName + ", point=" + point + ", teacher=" + teacher
				+ ", teacherStatus=" + teacherStatus + ", department=" + department + ", type=" + type
				+ ", timeAndPosition=" + timeAndPosition + ", week=" + week + ", startWeek=" + startWeek + ", endWeek="
				+ endWeek + ", alternation=" + alternation + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", locationId=" + locationId + ", district=" + district + ", build=" + build + ", floor=" + floor
				+ ", room=" + room + ", book=" + book + ", year=" + year + ", term=" + term + ", grade=" + grade
				+ ", status=" + status + ", classDes=" + classDes + ", remark=" + remark + ", kind=" + kind + "]";
	}
	
	
}
