package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.LiveClassStatisticsModel;

public interface ILiveClassStatisticsDao {
	
	public List<LiveClassStatisticsModel> getStatisticsByTimeRange(String beginTime, String endTime);
	
	public void addLiveClassStatistics(LiveClassStatisticsModel model);
		
}