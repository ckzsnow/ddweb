package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserStudyRecordModel;

public class UserStudyRecordMapper implements RowMapper<UserStudyRecordModel> {
	public UserStudyRecordModel mapRow(ResultSet rs, int index) throws SQLException {
		UserStudyRecordModel u = new UserStudyRecordModel(rs.getString("user_id"), 
				rs.getLong("course_id"), rs.getInt("course_type"),
				rs.getInt("pay_status"), rs.getString("name"), 
				rs.getString("teahcer"), rs.getString("image"), rs.getTimestamp("create_time"));
		return u;
	}
}