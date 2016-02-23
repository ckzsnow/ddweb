package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.UserCollectionModel;

public interface IUserCollectionDao {

	public List<UserCollectionModel> getUserCollectionByUserId(String userId);
	
	public boolean addUserCollection(UserCollectionModel userCollectionModel);
	
	public boolean isFinishCollection(String userId, long courseId);
	
	public List<LiveCourseModel> getUserCollectionOpenCourse(String userId);
	
	public List<LiveCourseModel> getUserCollectionLiveCourse(String userId);
	
	public boolean delUserCollection(String userId, long courseId);
		
}