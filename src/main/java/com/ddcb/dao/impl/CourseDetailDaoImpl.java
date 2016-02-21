package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.mapper.CourseDetailMapper;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;

@Repository("courseDetailDao")
public class CourseDetailDaoImpl implements ICourseDetailDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CourseDetailDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<CourseDetailModel> getCourseDetailByCourseId(long id) {
		String sql = "select * from course_detail where id = ?";
		List<CourseDetailModel> courseDetailModelList = null;
		try {
			courseDetailModelList = jdbcTemplate.query(sql,
					new Object[] { id }, new RowMapperResultSetExtractor<CourseDetailModel>(
							new CourseDetailMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseDetailModelList;
	}

	@Override
	public boolean addCourseDetail(CourseDetailModel courseDetailModel) {
		try{
			String sql= "insert into course_detail(id, sub_title, videosrc, teacher_image, teacher_name, teacher_position, teacher_info, crowd, details, video_image, create_time, course_time_length) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			int num = jdbcTemplate.update(sql, 
					courseDetailModel.getId(), courseDetailModel.getSubTitle(), courseDetailModel.getVideosrc(),
					courseDetailModel.getTeacher_image(), courseDetailModel.getTeacher_name(), 
					courseDetailModel.getTeacher_position(),
					courseDetailModel.getTeacher_info(), courseDetailModel.getCrowd(),
					courseDetailModel.getDetails(), courseDetailModel.getVideo_image(),
					courseDetailModel.getCreateTime(), courseDetailModel.getCourse_time_length());
			return num > 0;
		}catch(Exception e){
			logger.debug("exception : {}", e.toString());
		}
		return false;
	}
}