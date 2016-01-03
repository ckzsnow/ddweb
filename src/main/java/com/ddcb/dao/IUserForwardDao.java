package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserForwardModel;

public interface IUserForwardDao {

	public UserForwardModel getUserForwardByUserId(String userId);
	
	public List<UserForwardModel> getAllUserForward();
	
	public boolean addUserForward(UserForwardModel userForwardModel);
		
}