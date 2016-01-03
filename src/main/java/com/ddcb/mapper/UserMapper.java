package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserModel;

public class UserMapper implements RowMapper<UserModel> {
	public UserModel mapRow(ResultSet rs, int index) throws SQLException {
		UserModel u = new UserModel(rs.getString("user_id"), rs.getString("user_pwd"), rs.getInt("user_type"),
				rs.getTimestamp("create_time"));
		return u;
	}
}