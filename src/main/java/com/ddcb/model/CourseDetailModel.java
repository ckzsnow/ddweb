package com.ddcb.model;

import java.sql.Timestamp;

public class CourseDetailModel {

	private Long id;
	private String subTitle;
	private String videosrc;
	private String teacher_image;
	private String teacher_name;
	private String teacher_position;
	private String teacher_info;
	private String crowd;
	private String details;
	private String video_image;
	private Timestamp createTime;
	private String course_time_length;
	
	private String course_date;
	private String course_length;
	private String name;
	
	public CourseDetailModel(){}
	
	public CourseDetailModel(Long id, String subTitle, String videosrc, String teacher_image, String teacher_name, String teacher_position,
			String teacher_info, String crowd, String details, String video_image, Timestamp createTime, String course_time_length) {
		super();
		this.id = id;
		this.subTitle = subTitle;
		this.videosrc = videosrc;
		this.teacher_image = teacher_image;
		this.teacher_name = teacher_name;
		this.teacher_position = teacher_position;
		this.teacher_info = teacher_info;
		this.crowd = crowd;
		this.details = details;
		this.video_image = video_image;
		this.createTime = createTime;
		this.course_time_length = course_time_length;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getVideosrc() {
		return videosrc;
	}

	public void setVideosrc(String videosrc) {
		this.videosrc = videosrc;
	}

	public String getTeacher_image() {
		return teacher_image;
	}

	public void setTeacher_image(String teacher_image) {
		this.teacher_image = teacher_image;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_position() {
		return teacher_position;
	}

	public void setTeacher_position(String teacher_position) {
		this.teacher_position = teacher_position;
	}

	public String getTeacher_info() {
		return teacher_info;
	}

	public void setTeacher_info(String teacher_info) {
		this.teacher_info = teacher_info;
	}

	public String getCrowd() {
		return crowd;
	}

	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getVideo_image() {
		return video_image;
	}

	public void setVideo_image(String video_image) {
		this.video_image = video_image;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCourse_date() {
		return course_date;
	}

	public void setCourse_date(String course_date) {
		this.course_date = course_date;
	}

	public String getCourse_length() {
		return course_length;
	}

	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse_time_length() {
		return course_time_length;
	}

	public void setCourse_time_length(String course_time_length) {
		this.course_time_length = course_time_length;
	}

	@Override
	public String toString() {
		return "CourseDetailModel [id=" + id + ", subTitle=" + subTitle + ", videosrc=" + videosrc + ", teacher_image="
				+ teacher_image + ", teacher_name=" + teacher_name + ", teacher_position=" + teacher_position
				+ ", teacher_info=" + teacher_info + ", crowd=" + crowd + ", details=" + details + ", video_image="
				+ video_image + ", createTime=" + createTime + ", course_time_length=" + course_time_length
				+ ", course_date=" + course_date + ", course_length=" + course_length + ", name=" + name + "]";
	}
	
}
