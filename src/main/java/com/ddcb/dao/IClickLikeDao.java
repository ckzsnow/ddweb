package com.ddcb.dao;

import com.ddcb.model.ClickLikeModel;

public interface IClickLikeDao {
	
	public boolean addClickLike(ClickLikeModel clm);
	
	public ClickLikeModel getClickLike(long questionId, String openId);
	
	public boolean updateClickLike(long questionId, String openId, int clickLike);
		
}