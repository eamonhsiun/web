package com.wemeow.web.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wemeow.web.system.entity.Lesson;



@Repository
public interface LessonSortDao {
	List<Lesson> selectLsnPubRequired();
	List<Lesson> selectLsnPubOptional();
	List<Lesson> selectLsnPlan();
//	List<Lesson> selectLsnWhu();
//	List<Lesson> selectByLocation(@Param("locationId")int locationId,@Param("week")int week);
//	
//	void insertNewLesson(Lesson lesson);
//	void updateLocationId(Lesson lesson);
}
