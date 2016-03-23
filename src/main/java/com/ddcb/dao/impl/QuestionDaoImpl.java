package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
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
	public List<QuestionModel> getAllQuestionByCourseId(Long courseId, int page, int countPerPage) {
		List<QuestionModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * countPerPage;
		try {
			String sql = "select a.*, b.user_nickname, b.headimgurl, DATE_FORMAT(a.create_time,'%Y-%m-%d %T') as create_time_readable from user_question as a left join user_openid as b on a.open_id=b.open_id where a.course_id=? order by a.click_like desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{courseId, beginIndex, countPerPage}, new RowMapperResultSetExtractor<QuestionModel>(
							new QuestionMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean addQuestion(QuestionModel qm) {
		try{
			String sql= "insert into user_question(open_id, question, course_id, create_time) values (?,?,?,?)";
			int num = jdbcTemplate.update(sql, qm.getOpen_id(), qm.getQuestion(),
					qm.getCourse_id(), qm.getCreate_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updateClickLike(Long id) {
		String sql = "update user_question set click_like=click_like+1 where id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, id);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
}