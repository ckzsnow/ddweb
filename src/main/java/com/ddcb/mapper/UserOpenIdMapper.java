package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.ddcb.model.UserOpenIdModel;

public class UserOpenIdMapper implements RowMapper<UserOpenIdModel> {
	public UserOpenIdModel mapRow(ResultSet rs, int index) throws SQLException {
		UserOpenIdModel u = new UserOpenIdModel(rs.getString("open_id"), rs.getString("user_nickname"),
				rs.getString("user_sex"), rs.getString("user_provice"),
				rs.getString("user_city"), rs.getString("headimgurl"));
		return u;
	}
}