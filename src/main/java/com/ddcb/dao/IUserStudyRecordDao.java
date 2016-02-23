package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.UserStudyRecordModel;

public interface IUserStudyRecordDao {

	public List<UserStudyRecordModel> getUserStudyRecordByUserId(String userId);
	
	public boolean addUserStudyRecord(UserStudyRecordModel userStudyRecordModel);
	
	public boolean isFinishAddUserStudyRecord(String userId, long courseId);
	
	public boolean updateUserStudyRecord(String userId, long courseId);
	
	public boolean delUserStudyRecord(String userId, long courseId);
	
	public List<LiveCourseModel> getUserStudyRecord(String userId);
		
}