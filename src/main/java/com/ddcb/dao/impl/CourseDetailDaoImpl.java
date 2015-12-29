package com.ddcb.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.mapper.CourseDetailMapper;
import com.ddcb.model.CourseDetailModel;

@Repository("courseDetailDao")
public class CourseDetailDaoImpl implements ICourseDetailDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CourseDetailDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CourseDetailModel getCourseDetailByCourseId(long id) {
		String sql = "select * from course_detail where id = ?";
		CourseDetailModel courseDetailModel = null;
		try {
			courseDetailModel = jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new CourseDetailMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseDetailModel;
	}
}