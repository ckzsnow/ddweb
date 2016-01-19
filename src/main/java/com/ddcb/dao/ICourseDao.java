package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseModel;

public interface ICourseDao {

	public CourseModel getCourseByCourseId(long id);
	
	public long addCourse(CourseModel courseModel);
	
	public List<CourseModel> getAllCourse();
	
	public List<CourseModel> getAllOpenCourse();
	
	public List<CourseModel> getAllRecentCourse();
	
	public List<CourseModel> getAllUserPayedCourseRecentCourse();
		
}