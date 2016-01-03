package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserDao;
import com.ddcb.mapper.UserMapper;
import com.ddcb.model.UserModel;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserModel getUserByUserId(String userId) {
		String sql = "select * from user where user_id = ?";
		UserModel userModel = null;
		try {
			userModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId }, new UserMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return userModel;
	}
	
	@Override
	public List<UserModel> getAllUser() {
		List<UserModel> list = null;
		try {
			String sql = "select * from user";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserModel>(
							new UserMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addUser(UserModel userModel) {		
		try{
			String sql= "insert into user(user_id, user_pwd, user_type, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, userModel.getUser_id(), userModel.getUser_pwd(), userModel.getUser_type(),
					userModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updateUserPwd(String userId, String userPwd) {
		String sql = "update user set user_pwd=? where user_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, userPwd, userId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
}