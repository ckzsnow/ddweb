package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.BannerModel;

public interface IBannerDao {

	public List<BannerModel> getAllBanner();
	
	public boolean updateBanner(String id, String fileName, long courseId);
		
}