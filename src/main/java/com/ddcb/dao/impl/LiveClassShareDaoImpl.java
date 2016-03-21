package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IBannerDao;
import com.ddcb.dao.ILiveClassShareDao;
import com.ddcb.mapper.BannerMapper;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.mapper.LiveClassShareMapper;
import com.ddcb.model.BannerModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveCourseShareModel;

@Repository("liveClassShareDao")
public class LiveClassShareDaoImpl implements ILiveClassShareDao {

	private static final Logger logger = LoggerFactory
			.getLogger(LiveClassShareDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<LiveCourseShareModel> getAllLiveClassShare() {
		List<LiveCourseShareModel> list = null;
		try {
			String sql = "select a.*, c.id as course_id, c.name from live_class_share as a left join course as c on c.id=a.id";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<LiveCourseShareModel>(
							new LiveClassShareMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean updateLiveClassShare(LiveCourseShareModel lcsm) {
		String sql = "update live_class_share set id=?, image=?, link=?, title=? where week=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, lcsm.getId(), lcsm.getImage(),
					lcsm.getLink(), lcsm.getTitle(), lcsm.getWeekDay());
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public LiveCourseShareModel getLiveClassShareByWeekDay(int weekDay) {
		String sql = "select *, 0 as course_id, 123 as name from live_class_share where week = ?";
		LiveCourseShareModel liveCourseShareModel = null;
		try {
			liveCourseShareModel = jdbcTemplate.queryForObject(sql,
					new Object[] { weekDay }, new LiveClassShareMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return liveCourseShareModel;
	}

}