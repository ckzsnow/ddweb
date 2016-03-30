package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveClassApplyModel;
import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.LiveCourseShareModel;
import com.ddcb.model.SelectCourseModel;

public interface ICourseDao {

	public void updateCourseStudyPeopleCount();
	
	public void updateCourseStudyPeopleCount(Long courseId);
	
	public void updateCourseStudyPeopleCountForCount(Long courseId, Integer study_people_count);
	
	public CourseModel getCourseByCourseId(long id);
	
	public long addCourse(CourseModel courseModel);
	
	public List<CourseModel> getAllCourse();
	
	public List<CourseModel> getAllLiveClass();
	
	public List<CourseModel> getAllOpenCourse(int page, int count);
	
	public List<CourseModel> getAllOpenCourse();
	
	public List<CourseModel> getAllOpenCourseByCondition(int page, int count, String field, String industry, String competency);
		
	public List<LiveCourseModel> getAllLiveCourse(int page, int count, String userId);
	
	public List<LiveCourseModel> getAllFinishedLiveCourse(int page, int count, String userId);
	
	public List<LiveCourseModel> getAllLiveCourseByCondition(int page, int count, String field, String industry, String competency, String userId);
	
	public List<LiveCourseModel> getAllFinishedLiveCourseByCondition(int page, int count, String field, String industry, String competency, String userId);
	
	public List<CourseModel> getAllUserPayedCourseRecentCourse();
	
	public List<SelectCourseModel> getLatestCourse();
	
	public List<SelectCourseModel> getHotestCourse();
	
	public List<CourseModel> getOpenCourseByCondition(String openId, int page, int count, String type, String field, String industry, String competeny, String grade, String key);
		
	public List<LiveCourseShareModel> getLiveClassShare();
	
	public List<LiveClassApplyModel> getAllLiveClassApply();
	
	public List<LiveClassApplyModel> getAllOpenClassStudyCount();
	
	public List<LiveClassApplyModel> getAllSelectLiveCourse();

}