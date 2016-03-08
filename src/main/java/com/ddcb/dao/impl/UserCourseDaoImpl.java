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
	
	public List<CourseModel> getUserCourseByUserIdAndPayStatus(String userId, int payStatus) {
		List<CourseModel> list = null;
		try {
			String sql = "select * from user_course as a inner join course as b on b.id=a.course_id and a.user_id=? and a.pay_status=?";
			list = jdbcTemplate.query(sql, new Object[]{userId, payStatus}, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId) {
		UserCourseModel ucm = null;
		try {
			String sql = "select * from user_course where user_id=? and course_id=? ";
			ucm = jdbcTemplate.queryForObject(sql, new Object[]{userId, courseId}, new UserCourseMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return ucm;
	}

	@Override
	public boolean addUserCourseModel(UserCourseModel userCourseModel) {
		try{
			String sql= "insert into user_course(user_id, trade_no, course_id, create_time) values (?,?,?,?)";
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

	@Override
	public boolean updatePayStatusByTradeNo(String userId, String tradeNo, int payStatus) {
		String sql = "update user_course set pay_status=? where user_id=? and trade_no=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, payStatus, userId, tradeNo);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId, int payStatus) {
		UserCourseModel ucm = null;
		try {
			String sql = "select * from user_course where user_id=? and course_id=? and pay_status=?";
			ucm = jdbcTemplate.queryForObject(sql, new Object[]{userId, courseId, payStatus}, new UserCourseMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return ucm;
	}

	@Override
	public List<UserCourseModel> getAllUserPayedCourse(String userId) {
		List<UserCourseModel> list = null;
		try {
			String sql = "select * from user_course where user_id=? and pay_status=?";
			list = jdbcTemplate.query(sql, new Object[]{userId, 1}, new RowMapperResultSetExtractor<UserCourseModel>(
							new UserCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean updateTradeNo(String userId, long courseId, String tradeNo) {
		String sql = "update user_course set trade_no=? where user_id=? and course_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, tradeNo, userId, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public UserCourseModel newGetUserCourseByUserIdAndCourseId(String userId, long courseId) {
		UserCourseModel ucm = null;
		try {
			String sql = "select * from user_course where user_id=? and course_id=?";
			ucm = jdbcTemplate.queryForObject(sql, new Object[]{userId, courseId}, new UserCourseMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return ucm;
	}

	@Override
	public List<CourseModel> getUserBuyClass(String userId) {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade,c.parent_id, 0 as has_collection, c.study_people_count, 0 as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c INNER JOIN user_course as a on a.course_id=c.id and a.user_id=? and a.pay_status=1 order by c.course_date asc";
			list = jdbcTemplate.query(sql, new Object[]{userId}, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
}