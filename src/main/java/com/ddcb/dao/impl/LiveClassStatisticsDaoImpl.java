package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.ILiveClassStatisticsDao;
import com.ddcb.dao.IUserDao;
import com.ddcb.mapper.LiveClassStatisticsMapper;
import com.ddcb.mapper.UserMapper;
import com.ddcb.model.LiveClassStatisticsModel;
import com.ddcb.model.UserModel;

@Repository("liveClassStatisticsDao")
public class LiveClassStatisticsDaoImpl implements ILiveClassStatisticsDao {

	private static final Logger logger = LoggerFactory
			.getLogger(LiveClassStatisticsDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<LiveClassStatisticsModel> getStatisticsByTimeRange(String beginTime, String endTime) {
		List<LiveClassStatisticsModel> list = null;
		try {
			String sql = "select * from live_class_statistics where create_time>=? and create_time<=?";
			list = jdbcTemplate.query(sql, new Object[]{beginTime, endTime}, new RowMapperResultSetExtractor<LiveClassStatisticsModel>(
							new LiveClassStatisticsMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public void addLiveClassStatistics(LiveClassStatisticsModel model) {
		try{
			String sql= "insert into live_class_statistics(open_id, course_id, play_status, create_time) values (?,?,?,?)";
			jdbcTemplate.update(sql, model.getOpen_id(), 
					model.getCourse_id(), model.getPlay_status(), model.getCreate_time());
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}	
	}

}