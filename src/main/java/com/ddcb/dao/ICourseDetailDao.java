package com.ddcb.dao;

import com.ddcb.model.CourseDetailModel;

public interface ICourseDetailDao {

	public CourseDetailModel getCourseDetailByCourseId(long id);
	
	public boolean addCourseDetail(CourseDetailModel courseDetailModel);
		
}