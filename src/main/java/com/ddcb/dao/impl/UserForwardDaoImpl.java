package com.ddcb.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ddcb.dao.IUserForwardDao;
import com.ddcb.mapper.UserForwardMapper;
import com.ddcb.model.UserForwardModel;

@Repository("userForwardDao")
public class UserForwardDaoImpl implements IUserForwardDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserForwardDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserForwardModel getUserForwardByUserIdAndCourseId(String userId, long courseId) {
		String sql = "select * from user_forward where user_id=? and course_id=?";
		UserForwardModel userForwardModel = null;
		try {
			userForwardModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId, courseId }, new UserForwardMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return userForwardModel;
	}

	@Override
	public boolean addUserForward(UserForwardModel userForwardModel) {
		try{
			String sql= "insert into user_forward(user_id, screenshot, course_id, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, userForwardModel.getUser_id(), userForwardModel.getScreenshot(),
					userForwardModel.getCourse_id(), userForwardModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.debug("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updateScreenShot(UserForwardModel userForwardModel) {
		String sql = "update user_forward set screenshot=? where user_id=? and course_id = ?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, userForwardModel.getScreenshot(), userForwardModel.getUser_id(),
					userForwardModel.getCourse_id());
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
}