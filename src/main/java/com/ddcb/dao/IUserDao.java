package com.ddcb.dao;

import java.util.List;
import com.ddcb.model.UserModel;

public interface IUserDao {

	public UserModel getUserByOpenId(String openId);
	
	public List<UserModel> getAllUser();
	
	public boolean addUser(UserModel userModel);
		
}