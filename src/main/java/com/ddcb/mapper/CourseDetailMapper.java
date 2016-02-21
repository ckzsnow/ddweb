package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.CourseDetailModel;

public class CourseDetailMapper implements RowMapper<CourseDetailModel> {
	public CourseDetailModel mapRow(ResultSet rs, int index) throws SQLException {
		CourseDetailModel u = new CourseDetailModel(rs.getLong("id"), rs.getString("sub_title"), rs.getString("videosrc"), rs.getString("teacher_image"),
				rs.getString("teacher_name"), rs.getString("teacher_position"), rs.getString("teacher_info"), rs.getString("crowd"),
				rs.getString("details"), rs.getString("video_image"), rs.getTimestamp("create_time"), rs.getString("course_time_length"));
		return u;
	}
}