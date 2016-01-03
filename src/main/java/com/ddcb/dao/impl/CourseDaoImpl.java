package com.ddcb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
			String sql = "select * from course as c where (select date_add(c.course_date, interval c.course_length minute)) <= current_timestamp";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<CourseModel> getAllRecentCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select * from course as c where (select date_add(c.course_date, interval c.course_length minute)) > current_timestamp";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public long addCourse(CourseModel courseModel) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					String sql = "insert into course(name, course_abstract, teacher, image, course_time, course_date, course_length, create_time) values (?,?,?,?,?,?,?,?)";
					PreparedStatement ps = connection.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, courseModel.getName());
					ps.setString(2, courseModel.getCourse_abstract());
					ps.setString(3, courseModel.getTeacher());
					ps.setString(4, courseModel.getImage());
					ps.setString(5, courseModel.getCourse_time());
					ps.setTimestamp(6, courseModel.getCourse_date());
					ps.setString(7, courseModel.getCourse_length());
					ps.setTimestamp(8, courseModel.getCreate_time());
					return ps;
				}
			}, keyHolder);
			logger.debug("addCourse result : {}", keyHolder.getKey().longValue());
			return keyHolder.getKey().longValue();
		} catch (DataAccessException e) {
			logger.error("addCourse error." + e.toString());
		}
		return -1;
	}
}