package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserStudyRecordModel;

public interface IUserStudyRecordDao {

	public List<UserStudyRecordModel> getUserStudyRecordByUserId(String userId);
	
	public boolean addUserStudyRecord(UserStudyRecordModel userStudyRecordModel);
	
	public boolean isFinishAddUserStudyRecord(String userId, long courseId);
		
}