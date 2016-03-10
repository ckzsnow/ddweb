package com.ddcb.dao.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IWeixinUserDao;
import com.ddcb.dao.IWeixinUserLogDao;
import com.ddcb.mapper.WeixinUserMapper;
import com.ddcb.model.WeixinUserModel;

@Repository("weixinUserLogDao")
public class WeixinUserLogDaoImpl implements IWeixinUserLogDao {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinUserLogDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WeixinUserModel getWeixinUserByUserId(String userId) {
		String sql = "select * from weixin_user_log where user_id = ?";
		WeixinUserModel weixinUserModel = null;
		try {
			weixinUserModel = jdbcTemplate.queryForObject(sql,
					new Object[] { userId }, new WeixinUserMapper());
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return weixinUserModel;
	}
	
	@Override
	public boolean addWeixinUser(WeixinUserModel weixinUserModel) {		
		try{
			String sql= "insert into weixin_user_log(user_id, user_type, trade_no, create_time, pay_status, expiration_time) values (?,?,?,?,?,?)";
			int num = jdbcTemplate.update(sql, weixinUserModel.getUser_id(), weixinUserModel.getUser_type(),
					weixinUserModel.getTrade_no(), weixinUserModel.getCreate_time(),weixinUserModel.getPay_status(), weixinUserModel.getExpiration_time());
			return num > 0;
		}catch(Exception e){
			logger.error("exception : {}", e.toString());
		}
		return false;
	}

	@Override
	public boolean updateWeixinUserBeforePay(String userId, String tradeNo, Integer userType) {
		String sql = "update weixin_user_log set trade_no=?, user_type=? where user_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, tradeNo, userType, userId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}

	@Override
	public boolean updateWeixinUserAfterPay(String userId, String tradeNo, Integer userType, Timestamp expirationTime) {
		String sql = "update weixin_user_log set trade_no=?, pay_status=?, expiration_time=? where user_id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, tradeNo, 1, expirationTime, userId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
}