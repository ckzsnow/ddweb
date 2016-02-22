package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveCourseModel;

public interface ICourseDao {

	public CourseModel getCourseByCourseId(long id);
	
	public long addCourse(CourseModel courseModel);
	
	public List<CourseModel> getAllCourse();
	
	public List<CourseModel> getAllOpenCourse(int page, int count);
	
	public List<CourseModel> getAllOpenCourse();
	
	public List<CourseModel> getAllOpenCourseByCondition(int page, int count, String field, String industry, String competency);
		
	public List<LiveCourseModel> getAllLiveCourse(int page, int count, String userId);
	
	public List<LiveCourseModel> getAllLiveCourseByCondition(int page, int count, String field, String industry, String competency, String userId);
	
	public List<CourseModel> getAllUserPayedCourseRecentCourse();
		
}