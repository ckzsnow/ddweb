package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseModel;
import com.ddcb.model.UserCourseModel;

public interface IUserCourseDao {

	public List<UserCourseModel> getUserCourseByUserId(String userId);
	
	public List<CourseModel> getUserCourseByUserIdAndPayStatus(String userId, int payStatus);
	
	public List<UserCourseModel> getAllUserCourseByHasUpload();
	
	public UserCourseModel newGetUserCourseByUserIdAndCourseId(String userId, long courseId);
	
	public List<UserCourseModel> getAllUserPayedCourse(String userId);
	
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId);
	
	public UserCourseModel getUserCourseByUserIdAndCourseId(String userId, long courseId, int payStatus);
	
	public boolean addUserCourseModel(UserCourseModel userCourseModel);
	
	public boolean updatePayStatus(String userId, long courseId, int payStatus);
	
	public boolean updatePayStatusByTradeNo(String userId, String tradeNo, int payStatus);
	
	public boolean updateForwardStatus(String userId, long courseId, int forwardStatus);
	
	public boolean updateTradeNo(String userId, long courseId, String tradeNo);
	
	public List<CourseModel> getUserBuyClass(String userId);
		
}