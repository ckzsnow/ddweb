package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserCollectionDao;
import com.ddcb.mapper.UserCollectionMapper;
import com.ddcb.model.UserCollectionModel;

@Repository("userCollectionDao")
public class UserCollectionDaoImpl implements IUserCollectionDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserCollectionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserCollectionModel> getUserCollectionByUserId(String userId) {
		List<UserCollectionModel> list = null;
		try {
			String sql = "select a.user_id, a.course_id, c.course_type, b.pay_status, c.name, c.teacher, c.image, a.create_time from (user_collection as a left join user_course as b on a.user_id=b.user_id and a.course_id=b.course_id) left join course as c on a.course_id=c.id order by a.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserCollectionModel>(
							new UserCollectionMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addUserCollection(UserCollectionModel userCollectionModel) {
		try{
			String sql= "insert into user_collection(user_id, course_id, create_time) values (?,?,?)";
			int num = jdbcTemplate.update(sql, userCollectionModel.getUser_id(), userCollectionModel.getCourse_id(),
					userCollectionModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}
	
	@Override
	public boolean isFinishCollection(String userId, long courseId) {
		String sql = "select * from user_collection where user_id = ? and course_id=?";
		UserCollectionModel userCollectionModel = null;
		try {
			userCollectionModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId, courseId }, new UserCollectionMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return userCollectionModel != null;
	}

}
