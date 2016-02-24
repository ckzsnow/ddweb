package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.BannerModel;

public class BannerMapper implements RowMapper<BannerModel> {
	public BannerModel mapRow(ResultSet rs, int index) throws SQLException {
		BannerModel u = new BannerModel(rs.getString("id"), rs.getString("file_name"), 
				rs.getTimestamp("create_time"), rs.getLong("course_id"));
		return u;
	}
}