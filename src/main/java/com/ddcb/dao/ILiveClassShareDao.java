package com.ddcb.dao;

import java.util.List;
import com.ddcb.model.LiveCourseShareModel;

public interface ILiveClassShareDao {

	public List<LiveCourseShareModel> getAllLiveClassShare();
	
	public LiveCourseShareModel getLiveClassShareByWeekDay(String weekDay);
	
	public LiveCourseShareModel getLiveClassShareByCourseId(long id);
	
	public boolean updateLiveClassShare(String link, String week);
	
	public boolean addLiveClassShare(LiveCourseShareModel lcsm);
		
}