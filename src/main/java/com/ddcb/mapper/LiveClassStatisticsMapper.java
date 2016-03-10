package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.LiveClassStatisticsModel;
import com.ddcb.model.UserModel;

public class LiveClassStatisticsMapper implements RowMapper<LiveClassStatisticsModel> {
	public LiveClassStatisticsModel mapRow(ResultSet rs, int index) throws SQLException {
		LiveClassStatisticsModel u = new LiveClassStatisticsModel(rs.getString("open_id"), rs.getLong("course_id"), rs.getString("play_status"),
				rs.getTimestamp("create_time"));
		return u;
	}
}