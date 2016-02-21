package com.ddcb.dao;

import java.sql.Timestamp;

import com.ddcb.model.WeixinUserModel;

public interface IWeixinUserDao {

	public WeixinUserModel getWeixinUserByUserId(String userId);
	
	public boolean addWeixinUser(WeixinUserModel weixinUserModel);
	
	public boolean updateWeixinUser(String userId, String tradeNo, int pay_status, Timestamp expirationTime);
			
}