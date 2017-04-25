package com.wemeow.web.moment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wemeow.web.moment.entity.Moment;


@Repository
public interface MomentDao {

	int insertNewMoment(Moment moment);

	
	List<Moment> selectPublicMoments(
			@Param("userId") int userId,
			@Param("start") int start,
			@Param("rows") int rows);
	
	
	List<Moment> selectAllMoments(
			@Param("userId") int userId,
			@Param("start") int start,
			@Param("rows") int rows);
	
	List<Moment> selectFriendMoments(
			@Param("userId") int userId,
			@Param("start") int start,
			@Param("rows") int rows);

	Moment selectMoment(int momentId);
	
}
