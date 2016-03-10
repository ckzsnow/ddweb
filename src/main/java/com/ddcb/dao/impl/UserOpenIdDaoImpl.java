package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserDao;
import com.ddcb.dao.IUserOpenIdDao;
import com.ddcb.mapper.UserMapper;
import com.ddcb.mapper.UserOpenIdDetailMapper;
import com.ddcb.mapper.UserOpenIdMapper;
import com.ddcb.model.UserModel;
import com.ddcb.model.UserOpenIdDetailModel;
import com.ddcb.model.UserOpenIdModel;

@Repository("userOpenIdDao")
public class UserOpenIdDaoImpl implements IUserOpenIdDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserOpenIdDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserOpenIdModel getUserByUserOpenId(String openId) {
		String sql = "select * from user_openid where open_id = ?";
		UserOpenIdModel userOpenIdModel = null;
		try {
			userOpenIdModel = jdbcTemplate.queryForObject(sql,
					new Object[] { openId }, new UserOpenIdMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return userOpenIdModel;
	}

	@Override
	public List<UserOpenIdDetailModel> getAllUser() {
		List<UserOpenIdDetailModel> list = null;
		try {
			String sql = "select uo.*, wu.* from user_openid as uo LEFT JOIN weixin_user as wu on uo.open_id=wu.user_id";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserOpenIdDetailModel>(
							new UserOpenIdDetailMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addUser(UserOpenIdModel model) {
		try{
			String sql= "insert into user_openid(open_id, user_nickname, user_sex, user_provice, user_city, headimgurl) values (?,?,?,?,?,?)";
			int num = jdbcTemplate.update(sql, 
					model.getOpen_id(), model.getUser_nickname(), model.getUser_sex(),
					model.getUser_province(), model.getUser_city(), model.getHeadimgurl());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updateUser(String user_nickname, String user_sex, String user_provice, String user_city,
			String headimgurl) {
		String sql = "update user_openid set user_nickname=?, user_sex=?, user_provice=?, user_city=?, headimgurl=? where open_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, user_nickname, user_sex, user_provice, user_city, headimgurl);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	
}