package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IClickLikeDao;
import com.ddcb.dao.IQuestionDao;
import com.ddcb.dao.IUserDao;
import com.ddcb.mapper.ClickLikeMapper;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.mapper.QuestionMapper;
import com.ddcb.mapper.UserMapper;
import com.ddcb.model.ClickLikeModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.QuestionModel;
import com.ddcb.model.UserModel;

@Repository("clickLikeDao")
public class ClickLikeDaoImpl implements IClickLikeDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ClickLikeDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean addClickLike(ClickLikeModel clm) {
		try{
			String sql= "insert into user_click_like(question_id, open_id, click_like, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, clm.getQuestion_id(), clm.getOpen_id(),
					clm.getClick_like(), clm.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}
	
	@Override
	public ClickLikeModel getClickLike(long questionId, String openId) {
		String sql = "select * from user_click_like where question_id=? and open_id=?";
		ClickLikeModel clickLikeModel = null;
		try {
			clickLikeModel = jdbcTemplate.queryForObject(sql,
					new Object[] { questionId, openId }, new ClickLikeMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return clickLikeModel;
	}

	@Override
	public boolean updateClickLike(long questionId, String openId, int clickLike) {
		String sql = "update user_click_like set clickLike=? where question_id=? and open_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, clickLike, questionId, openId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
}