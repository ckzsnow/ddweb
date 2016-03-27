package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.LiveCourseShareModel;

public class LiveClassShareMapper implements RowMapper<LiveCourseShareModel> {
	public LiveCourseShareModel mapRow(ResultSet rs, int index) throws SQLException {
		LiveCourseShareModel u = new LiveCourseShareModel(rs.getLong("id"), rs.getString("image"), rs.getString("link"),
				rs.getString("title"), rs.getString("week"), rs.getLong("course_id"), rs.getString("course_name"));
		return u;
	}
}