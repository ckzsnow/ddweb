package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveCourseModel;

public class LiveCourseMapper implements RowMapper<LiveCourseModel> {
	public LiveCourseModel mapRow(ResultSet rs, int index) throws SQLException {
		LiveCourseModel u = new LiveCourseModel(rs.getLong("id"), rs.getString("name"), rs.getString("course_abstract"),
				rs.getString("teacher"), rs.getString("image"), rs.getTimestamp("course_date"), rs.getString("course_date_readable"), 
				rs.getString("course_time"), rs.getString("course_length"), rs.getTimestamp("create_time"), rs.getInt("course_type"),
				rs.getString("course_field"), rs.getString("course_industry"), rs.getString("course_competency"), rs.getString("price"),
				rs.getString("has_collection"), rs.getInt("pay_status"), rs.getString("screenshot"), rs.getInt("donate_pay_status"));
		return u;
	}
}