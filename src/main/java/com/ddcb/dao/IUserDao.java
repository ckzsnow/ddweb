package com.ddcb.dao;

import java.util.List;
import com.ddcb.model.UserModel;

public interface IUserDao {

	public UserModel getUserByUserId(String userId);
	
	public List<UserModel> getAllUser();
	
	public boolean addUser(UserModel userModel);
	
	public boolean updateUserPwd(String userId, String userPwd);
		
}