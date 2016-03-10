package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserOpenIdDetailModel;
import com.ddcb.model.UserOpenIdModel;

public interface IUserOpenIdDao {

	public UserOpenIdModel getUserByUserOpenId(String openId);
	
	public List<UserOpenIdDetailModel> getAllUser();
	
	public boolean addUser(UserOpenIdModel model);
	
	public boolean updateUser(String user_nickname, String user_sex, String user_provice, String user_city, String headimgurl);
		
}