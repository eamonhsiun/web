package com.wemeow.web.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wemeow.web.system.entity.POI;



@Repository
public interface POIDao {
	List<POI> selectPOI();
	void insert(POI poi);

}
