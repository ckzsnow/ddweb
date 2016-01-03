package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserForwardModel;

public class UserForwardMapper implements RowMapper<UserForwardModel> {
	public UserForwardModel mapRow(ResultSet rs, int index) throws SQLException {
		UserForwardModel u = new UserForwardModel(rs.getString("user_id"), rs.getLong("course_id"), rs.getString("screenshot"), rs.getTimestamp("create_time"));
		return u;
	}
}