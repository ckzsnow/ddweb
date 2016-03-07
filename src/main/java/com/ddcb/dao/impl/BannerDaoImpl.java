package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IBannerDao;
import com.ddcb.mapper.BannerMapper;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.model.BannerModel;
import com.ddcb.model.CourseModel;

@Repository("bannerDao")
public class BannerDaoImpl implements IBannerDao {

	private static final Logger logger = LoggerFactory
			.getLogger(BannerDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<BannerModel> getAllBanner() {
		List<BannerModel> list = null;
		try {
			String sql = "select * from banner";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<BannerModel>(
							new BannerMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean updateBanner(String id, String fileName, long courseId) {
		String sql = "update banner set file_name=?, course_id=? where id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, fileName, courseId, id);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public List<CourseModel> getAllBannerCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, c.price as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c INNER JOIN banner as a on c.id=a.course_id";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}
	
}