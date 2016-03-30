package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.BannerModel;
import com.ddcb.model.LiveClassApplyModel;

public class LiveClassApplyMapper implements RowMapper<LiveClassApplyModel> {
	public LiveClassApplyModel mapRow(ResultSet rs, int index) throws SQLException {
		LiveClassApplyModel u = new LiveClassApplyModel(rs.getLong("id"), rs.getString("name"),
				rs.getString("teacher"), rs.getInt("total"));
		return u;
	}
}