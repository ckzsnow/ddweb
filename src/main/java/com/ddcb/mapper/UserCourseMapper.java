package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserCourseModel;

public class UserCourseMapper implements RowMapper<UserCourseModel> {
	public UserCourseModel mapRow(ResultSet rs, int index) throws SQLException {
		UserCourseModel u = new UserCourseModel(rs.getString("user_id"), rs.getInt("pay_status"), rs.getInt("forward_status"),
				rs.getLong("course_id"), rs.getString("upload_time"), rs.getString("screenshot"),
				rs.getTimestamp("create_time"));
		return u;
	}
}