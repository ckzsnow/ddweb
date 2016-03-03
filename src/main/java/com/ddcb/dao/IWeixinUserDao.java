package com.ddcb.dao;

import java.sql.Timestamp;

import com.ddcb.model.WeixinUserModel;

public interface IWeixinUserDao {

	public WeixinUserModel getWeixinUserByUserId(String userId);
	
	public boolean addWeixinUser(WeixinUserModel weixinUserModel);
	
	public boolean updateWeixinUserBeforePay(String userId, String tradeNo, Integer userType);
	
	public boolean updateWeixinUserAfterPay(String userId, String tradeNo, Integer userType, Timestamp expirationTime);
			
}