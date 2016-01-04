package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserCourseModel;

public interface IUserCourseDao {

	public List<UserCourseModel> getUserCourseByUserId(String userId);
	
	public List<UserCourseModel> getAllUserCourseByHasUpload();
	
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId);
	
	public boolean addUserCourseModel(UserCourseModel userCourseModel);
	
	public boolean updatePayStatus(String userId, long courseId, int payStatus);
	
	public boolean updateForwardStatus(String userId, long courseId, int forwardStatus);
		
}