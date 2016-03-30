package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserCourseDao;
import com.ddcb.dao.IUserLiveCoursePayDao;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.mapper.UserCourseMapper;
import com.ddcb.mapper.UserLiveCoursePayMapper;
import com.ddcb.model.CourseModel;
import com.ddcb.model.UserCourseModel;
import com.ddcb.model.UserLiveCoursePayModel;

@Repository("userLiveCoursePayDao")
public class UserLiveCoursePayDaoImpl implements IUserLiveCoursePayDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserLiveCoursePayDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserLiveCoursePayModel getUserCourseByUserIdAndCourseId(String userId, long courseId) {
		UserLiveCoursePayModel ucm = null;
		try {
			String sql = "select * from user_live_course_pay where user_id=? and course_id=? ";
			ucm = jdbcTemplate.queryForObject(sql, new Object[]{userId, courseId}, new UserLiveCoursePayMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return ucm;
	}

	@Override
	public boolean addUserLiveCoursePayModel(UserLiveCoursePayModel userCourseModel) {
		try{
			String sql= "insert into user_live_course_pay(user_id, trade_no, course_id, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, userCourseModel.getUser_id(), userCourseModel.getTradeNo(),
					userCourseModel.getCourse_id(), userCourseModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updatePayStatus(String userId, long courseId, int payStatus) {
		String sql = "update user_live_course_pay set pay_status=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, payStatus, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public boolean updatePayStatusByTradeNo(String userId, String tradeNo, int payStatus) {
		String sql = "update user_live_course_pay set pay_status=? where user_id=? and trade_no=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, payStatus, userId, tradeNo);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public boolean updateTradeNo(String userId, long courseId, String tradeNo) {
		String sql = "update user_live_course_pay set trade_no=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, tradeNo, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public List<UserLiveCoursePayModel> getAllUserCourse() {
		// TODO Auto-generated method stub
		return null;
	}
}