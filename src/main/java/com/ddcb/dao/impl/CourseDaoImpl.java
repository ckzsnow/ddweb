package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import com.ddcb.dao.ICourseDao;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.model.CourseModel;

@Repository("courseDao")
public class CourseDaoImpl implements ICourseDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CourseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CourseModel getCourseByCourseId(long id) {
		String sql = "select * from course where id = ?";
		CourseModel courseModel = null;
		try {
			courseModel = jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new CourseMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseModel;
	}

	@Override
	public List<CourseModel> getAllCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select * from course";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
}