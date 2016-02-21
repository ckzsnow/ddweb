package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.UserCollectionModel;

public interface IUserCollectionDao {

	public List<UserCollectionModel> getUserCollectionByUserId(String userId);
	
	public boolean addUserCollection(UserCollectionModel userCollectionModel);
	
	public boolean isFinishCollection(String userId, long courseId);
		
}