package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
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
	public UserForwardModel getUserForwardByUserId(String userId) {
		String sql = "select * from user_forward where user_id = ?";
		UserForwardModel userForwardModel = null;
		try {
			userForwardModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId }, new UserForwardMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return userForwardModel;
	}

	@Override
	public List<UserForwardModel> getAllUserForward() {
		List<UserForwardModel> list = null;
		try {
			String sql = "select * from user_forward";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserForwardModel>(
							new UserForwardMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addUserForward(UserForwardModel userForwardModel) {
		try{
			String sql= "insert into user_forward(user_id, screenshot, course_id, create_time) values (?,?,?)";
			int num = jdbcTemplate.update(sql, userForwardModel.getUser_id(), userForwardModel.getScreenshot(),
					userForwardModel.getCourse_id(), userForwardModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.debug("exception : {}", e.toString());
		}
		return false;
	}
}