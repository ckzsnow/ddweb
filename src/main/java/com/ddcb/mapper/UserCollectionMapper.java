package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserCollectionModel;

public class UserCollectionMapper implements RowMapper<UserCollectionModel> {
	public UserCollectionModel mapRow(ResultSet rs, int index) throws SQLException {
		UserCollectionModel u = new UserCollectionModel(rs.getString("user_id"), 
				rs.getLong("course_id"), rs.getInt("course_type"),
				rs.getInt("pay_status"), rs.getString("name"), 
				rs.getString("teahcer"), rs.getString("image"),rs.getTimestamp("create_time"));
		return u;
	}
}