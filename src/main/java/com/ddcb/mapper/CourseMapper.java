package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.CourseModel;

public class CourseMapper implements RowMapper<CourseModel> {
	public CourseModel mapRow(ResultSet rs, int index) throws SQLException {
		CourseModel u = new CourseModel(rs.getLong("id"), rs.getString("name"), rs.getString("course_abstract"),
				rs.getString("teacher"), rs.getString("image"), rs.getTimestamp("course_date"), rs.getString("course_date_readable"), 
				rs.getString("course_time"), rs.getString("course_length"), rs.getTimestamp("create_time"));
		return u;
	}
}