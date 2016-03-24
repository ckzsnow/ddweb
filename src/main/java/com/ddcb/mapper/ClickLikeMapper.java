package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.ClickLikeModel;
import com.ddcb.model.QuestionModel;
import com.ddcb.model.UserModel;

public class ClickLikeMapper implements RowMapper<ClickLikeModel> {
	public ClickLikeModel mapRow(ResultSet rs, int index) throws SQLException {
		ClickLikeModel u = new ClickLikeModel(rs.getLong("id"), rs.getLong("question_id"), rs.getString("open_id"), 
				rs.getInt("click_like"), rs.getTimestamp("create_time"));
		return u;
	}
}