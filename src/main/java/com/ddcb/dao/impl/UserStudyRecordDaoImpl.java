package com.ddcb.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IUserStudyRecordDao;
import com.ddcb.mapper.LiveCourseMapper;
import com.ddcb.mapper.UserStudyRecordMapper;
import com.ddcb.model.LiveCourseModel;
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
			String sql= "insert into user_study_record(user_id, course_id, update_time, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, userStudyRecordModel.getUser_id(), userStudyRecordModel.getCourse_id(),
					userStudyRecordModel.getUpdate_time(), userStudyRecordModel.getCreate_time());
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

	@Override
	public List<LiveCourseModel> getUserStudyRecord(String userId) {
		List<LiveCourseModel> list = null;
		try {
			String sql = "select ulcp.pay_status as donate_pay_status, uf.screenshot, !ISNULL(b.user_id) as has_collection, !ISNULL(b.user_id) as pay_status, c.id, c.price, c.course_field, c.course_industry, c.course_competency, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c inner JOIN user_study_record as b on c.id=b.course_id and b.user_id=? LEFT JOIN user_forward as uf on uf.user_id=? and uf.course_id=c.id LEFT JOIN user_live_course_pay as ulcp on ulcp.user_id=? and ulcp.course_id=c.id order by b.update_time desc";
			list = jdbcTemplate.query(sql, new Object[]{userId, userId, userId}, new RowMapperResultSetExtractor<LiveCourseModel>(
							new LiveCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean updateUserStudyRecord(String userId, long courseId) {
		String sql = "update user_study_record set update_time=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, new Timestamp(System.currentTimeMillis()), userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public boolean delUserStudyRecord(String userId, long courseId) {
		String sql = "delete from user_study_record where user_id=? and course_id=?";
		logger.debug("delUserStudyRecord, userId :{}, courseId :{}", userId, courseId);
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

}
