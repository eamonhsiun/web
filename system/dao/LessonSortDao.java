package com.wemeow.web.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wemeow.web.system.entity.LessonPreSort;



@Repository
public interface LessonSortDao {
	List<LessonPreSort> selectLsnPubRequired();
	List<LessonPreSort> selectLsnPubOptional();
	List<LessonPreSort> selectLsnPlan();
	List<LessonPreSort> selectLsnWhu();
//	List<Lesson> selectByLocation(@Param("locationId")int locationId,@Param("week")int week);
//	
	void insertNewLesson(LessonPreSort lesson);
	void updatePoiId(LessonPreSort lesson);
}
