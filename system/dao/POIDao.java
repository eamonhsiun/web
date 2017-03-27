package com.wemeow.web.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wemeow.web.system.entity.POI;



@Repository
public interface POIDao {
	void insert(POI poi);
	List<POI> selectPOI(double latitude,double longitude);
	List<POI> selectPOIByGeohash(String geohash);
	void updateGeohash(POI poi);

}
