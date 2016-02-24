package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.CourseModel;
import com.ddcb.model.SelectCourseModel;

public class SelectCourseMapper implements RowMapper<SelectCourseModel> {
	public SelectCourseModel mapRow(ResultSet rs, int index) throws SQLException {
		SelectCourseModel u = new SelectCourseModel(rs.getLong("id"), rs.getString("name"), 
				rs.getString("image"), rs.getString("course_length"), rs.getInt("people_count"));
		return u;
	}
}