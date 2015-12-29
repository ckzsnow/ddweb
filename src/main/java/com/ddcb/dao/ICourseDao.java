package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseModel;

public interface ICourseDao {

	public CourseModel getCourseByCourseId(long id);
	
	public List<CourseModel> getAllCourse();
		
}