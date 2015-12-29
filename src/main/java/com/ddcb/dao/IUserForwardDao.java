package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserForwardModel;

public interface IUserForwardDao {

	public UserForwardModel getUserForwardByOpenId(String openId);
	
	public List<UserForwardModel> getAllUserForward();
	
	public boolean addUserForward(UserForwardModel userForwardModel);
		
}