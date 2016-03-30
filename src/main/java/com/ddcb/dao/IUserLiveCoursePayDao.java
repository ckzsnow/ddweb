package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserLiveCoursePayModel;

public interface IUserLiveCoursePayDao {
	
	public UserLiveCoursePayModel getUserCourseByUserIdAndCourseId(String userId, long courseId);
	
	public List<UserLiveCoursePayModel> getAllUserCourse();
	
	public boolean addUserLiveCoursePayModel(UserLiveCoursePayModel userCourseModel);
	
	public boolean updatePayStatus(String userId, long courseId, int payStatus);
	
	public boolean updatePayStatusByTradeNo(String userId, String tradeNo, int payStatus);
	
	public boolean updateTradeNo(String userId, long courseId, String tradeNo);
		
}