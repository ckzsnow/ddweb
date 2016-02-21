package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseDetailModel;

public interface ICourseDetailDao {

	public List<CourseDetailModel> getCourseDetailByCourseId(long id);
	
	public boolean addCourseDetail(CourseDetailModel courseDetailModel);
		
}