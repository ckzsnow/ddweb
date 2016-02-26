package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.BannerModel;
import com.ddcb.model.CourseModel;

public interface IBannerDao {

	public List<BannerModel> getAllBanner();
	
	public List<CourseModel> getAllBannerCourse();
	
	public boolean updateBanner(String id, String fileName, long courseId);
		
}