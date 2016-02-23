package com.ddcb.model;

import java.sql.Timestamp;

public class UserStudyRecordModel {

	private String user_id;
	private Long course_id;
	private Timestamp update_time;
	private Timestamp create_time;
	
	public UserStudyRecordModel() {}

	public UserStudyRecordModel(String user_id, Long course_id, Timestamp update_time, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.course_id = course_id;
		this.update_time = update_time;
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserStudyRecordModel [user_id=" + user_id + ", course_id=" + course_id + ", update_time=" + update_time
				+ ", create_time=" + create_time + "]";
	}

}
