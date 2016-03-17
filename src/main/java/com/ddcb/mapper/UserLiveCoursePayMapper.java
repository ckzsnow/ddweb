package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserLiveCoursePayModel;

public class UserLiveCoursePayMapper implements RowMapper<UserLiveCoursePayModel> {
	public UserLiveCoursePayModel mapRow(ResultSet rs, int index) throws SQLException {
		UserLiveCoursePayModel u = new UserLiveCoursePayModel(rs.getString("user_id"), rs.getString("trade_no"), rs.getInt("pay_status"), rs.getInt("forward_status"),
				rs.getLong("course_id"), rs.getTimestamp("create_time"));
		return u;
	}
}