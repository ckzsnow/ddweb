package com.ddcb.dao;

import java.util.List;
import com.ddcb.model.LiveCourseShareModel;

public interface ILiveClassShareDao {

	public List<LiveCourseShareModel> getAllLiveClassShare();
	
	public LiveCourseShareModel getLiveClassShareByWeekDay(int weekDay);
	
	public boolean updateLiveClassShare(LiveCourseShareModel lcsm);
		
}