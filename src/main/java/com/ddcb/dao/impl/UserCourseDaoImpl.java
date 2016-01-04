package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserCourseDao;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.mapper.UserCourseMapper;
import com.ddcb.model.CourseModel;
import com.ddcb.model.UserCourseModel;

@Repository("userCourseDao")
public class UserCourseDaoImpl implements IUserCourseDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserCourseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserCourseModel> getUserCourseByUserId(String userId) {
		return null;
	}

	@Override
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId) {
		UserCourseModel ucm = null;
		try {
			String sql = "select a.user_id, a.course_id, a.pay_status, a.forward_status, a.create_time, b.screenshot, DATE_FORMAT(b.create_time,'%Y-%m-%d %T') as upload_time from user_course as a INNER JOIN user_forward as b on a.user_id = b.user_id and a.course_id=b.course_id and a.user_id=? and a.course_id=?";
			ucm = jdbcTemplate.queryForObject(sql, new Object[]{userId, courseId}, new UserCourseMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return ucm;
	}

	@Override
	public boolean addUserCourseModel(UserCourseModel userCourseModel) {
		try{
			String sql= "insert into user_course(user_id, pay_status, forward_status, course_id, create_time) values (?,?,?,?,?)";
			int num = jdbcTemplate.update(sql, userCourseModel.getUser_id(), 
					userCourseModel.getPay_status(), userCourseModel.getForward_status(),
					userCourseModel.getCourse_id(), userCourseModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updatePayStatus(String userId, long courseId, int payStatus) {
		String sql = "update user_course set pay_status=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, payStatus, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public boolean updateForwardStatus(String userId, long courseId, int forwardStatus) {
		String sql = "update user_course set forward_status=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, forwardStatus, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public List<UserCourseModel> getAllUserCourseByHasUpload() {
		List<UserCourseModel> list = null;
		try {
			String sql = "select a.user_id, a.course_id, a.pay_status, a.forward_status, a.create_time, b.screenshot, DATE_FORMAT(b.create_time,'%Y-%m-%d %T') as upload_time from user_course as a INNER JOIN user_forward as b on a.user_id = b.user_id and a.course_id=b.course_id order by b.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserCourseModel>(
							new UserCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
}