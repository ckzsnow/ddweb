package com.ddcb.model;

import java.sql.Timestamp;

public class BannerModel {

	private String id;
	private String file_name;
	private Long course_id;
	private Timestamp create_time;
	
	public BannerModel() {}

	public BannerModel(String id, String file_name, Timestamp create_time, Long courseId) {
		super();
		this.id = id;
		this.file_name = file_name;
		this.create_time = create_time;
		this.course_id = courseId;
	}

	@Override
	public String toString() {
		return "BannerModel [id=" + id + ", file_name=" + file_name + ", course_id=" + course_id + ", create_time="
				+ create_time + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	
}
