package com.ddcb.dao;

import com.ddcb.model.UserForwardModel;

public interface IUserForwardDao {

	public UserForwardModel getUserForwardByUserIdAndCourseId(String userId, long courseId);
	
	public boolean addUserForward(UserForwardModel userForwardModel);
	
	public boolean updateScreenShot(UserForwardModel userForwardModel);
		
}