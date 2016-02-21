package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserStudyRecordDao;
import com.ddcb.mapper.UserStudyRecordMapper;
import com.ddcb.model.UserStudyRecordModel;

@Repository("userStudyRecordDao")
public class UserStudyRecordDaoImpl implements IUserStudyRecordDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserStudyRecordDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserStudyRecordModel> getUserStudyRecordByUserId(String userId) {
		List<UserStudyRecordModel> list = null;
		try {
			String sql = "select a.user_id, a.course_id, c.course_type, b.pay_status, c.name, c.teacher, c.image, a.create_time from (user_collection as a left join user_course as b on a.user_id=b.user_id and a.course_id=b.course_id) left join course as c on a.course_id=c.id order by a.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<UserStudyRecordModel>(
							new UserStudyRecordMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addUserStudyRecord(UserStudyRecordModel userStudyRecordModel) {
		try{
			String sql= "insert into user_study_record(user_id, course_id, create_time) values (?,?,?)";
			int num = jdbcTemplate.update(sql, userStudyRecordModel.getUser_id(), userStudyRecordModel.getCourse_id(),
					userStudyRecordModel.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}
	
	@Override
	public boolean isFinishAddUserStudyRecord(String userId, long courseId) {
		String sql = "select * from user_study_record where user_id = ? and course_id=?";
		UserStudyRecordModel userStudyRecordModel = null;
		try {
			userStudyRecordModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId, courseId }, new UserStudyRecordMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return userStudyRecordModel != null;
	}

}
