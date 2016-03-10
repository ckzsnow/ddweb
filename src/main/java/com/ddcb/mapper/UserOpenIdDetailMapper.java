package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.UserOpenIdDetailModel;
import com.ddcb.model.UserOpenIdModel;

public class UserOpenIdDetailMapper implements RowMapper<UserOpenIdDetailModel> {
	public UserOpenIdDetailModel mapRow(ResultSet rs, int index) throws SQLException {
		UserOpenIdDetailModel u = new UserOpenIdDetailModel(rs.getString("open_id"), rs.getString("user_nickname"),
				rs.getString("user_sex"), rs.getString("user_provice"),
				rs.getString("user_city"), rs.getString("headimgurl"),rs.getString("user_id"), rs.getInt("user_type"),
				rs.getString("trade_no"), rs.getInt("pay_status"), rs.getTimestamp("expiration_time"),
				rs.getTimestamp("create_time"));
		return u;
	}
}