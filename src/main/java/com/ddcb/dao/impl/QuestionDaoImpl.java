package com.ddcb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IQuestionDao;
import com.ddcb.dao.IUserDao;
import com.ddcb.mapper.QuestionMapper;
import com.ddcb.mapper.UserMapper;
import com.ddcb.model.QuestionModel;
import com.ddcb.model.UserModel;

@Repository("questionDao")
public class QuestionDaoImpl implements IQuestionDao {

	private static final Logger logger = LoggerFactory
			.getLogger(QuestionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<QuestionModel> getAllQuestionByCourseId(String openId, Long courseId, int page, int countPerPage) {
		List<QuestionModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * countPerPage;
		try {
			String sql = "select c.click_like as current_click_like, a.*, b.user_nickname, b.headimgurl, DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time_readable from user_question as a left join user_openid as b on a.open_id=b.open_id left join user_click_like as c on c.open_id=? and c.question_id=a.id where a.course_id=? order by a.click_like desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{openId, courseId, beginIndex, countPerPage}, new RowMapperResultSetExtractor<QuestionModel>(
							new QuestionMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<QuestionModel> getAllQuestionByCourseId(Long courseId, int page, int countPerPage) {
		List<QuestionModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * countPerPage;
		try {
			String sql = "select 0 as current_click_like, a.*, b.user_nickname, b.headimgurl, DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time_readable from user_question as a left join user_openid as b on a.open_id=b.open_id where a.course_id=? order by a.click_like desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{courseId, beginIndex, countPerPage}, new RowMapperResultSetExtractor<QuestionModel>(
							new QuestionMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public long addQuestion(QuestionModel qm) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try{
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					String sql = "insert into user_question(open_id, question, course_id, create_time) values (?,?,?,?)";
					PreparedStatement ps = connection.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, qm.getOpen_id());
					ps.setString(2, qm.getQuestion());
					ps.setLong(3, qm.getCourse_id());
					ps.setTimestamp(4, qm.getCreate_time());
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().longValue();
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return -1;
	}

	@Override
	public boolean updateClickLike(Long id, int type) {
		String sqlInc = "update user_question set click_like=click_like+1 where id=?";
		String sqlDesc = "update user_question set click_like=click_like-1 where id=?";
		int affectedRows = 0;
		try {
			if(type == 0) {
				affectedRows = jdbcTemplate.update(sqlDesc, id);
			} else if(type == 1) {
				affectedRows = jdbcTemplate.update(sqlInc, id);
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
	
	@Override
	public boolean deleteUserQuestion(Long id) {
		String sql = "delete from user_question where id = ?";
		try {
			int affectedRows = jdbcTemplate.update(sql, id);
			return affectedRows != 0;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
}