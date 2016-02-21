package com.ddcb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ddcb.model.WeixinUserModel;

public class WeixinUserMapper implements RowMapper<WeixinUserModel> {
	public WeixinUserModel mapRow(ResultSet rs, int index) throws SQLException {
		WeixinUserModel u = new WeixinUserModel(rs.getString("user_id"), rs.getInt("user_type"),
				rs.getString("trade_no"), rs.getInt("pay_status"), rs.getTimestamp("expiration_time"),
				rs.getTimestamp("create_time"));
		return u;
	}
}