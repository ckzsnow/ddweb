package com.ddcb.model;

import java.sql.Timestamp;

public class CourseDetailModel {

	private Long id;
	private String videosrc;
	private String teacher_image;
	private String teacher_name;
	private String teacher_position;
	private String teacher_info;
	private String crowd;
	private String details;
	private Timestamp createTime;
	
	public CourseDetailModel(Long id, String videosrc, String teacher_image, String teacher_name, String teacher_position,
			String teacher_info, String crowd, String details, Timestamp createTime) {
		super();
		this.id = id;
		this.videosrc = videosrc;
		this.teacher_image = teacher_image;
		this.teacher_name = teacher_name;
		this.teacher_position = teacher_position;
		this.teacher_info = teacher_info;
		this.crowd = crowd;
		this.details = details;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CourseDetailModel [id=" + id + ", videosrc=" + videosrc + ", teacher_image=" + teacher_image
				+ ", teacher_name=" + teacher_name + ", teacher_position=" + teacher_position + ", teacher_info="
				+ teacher_info + ", crowd=" + crowd + ", details=" + details + ", createTime=" + createTime + "]";
	}
	
}
