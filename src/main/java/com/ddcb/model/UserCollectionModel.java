package com.ddcb.model;

import java.sql.Timestamp;

public class UserCollectionModel {

	private String user_id;
	private Long course_id;
	private Timestamp create_time;
	
	public UserCollectionModel() {}

	public UserCollectionModel(String user_id, Long course_id, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.course_id = course_id;
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

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserCollectionModel [user_id=" + user_id + ", course_id=" + course_id + ", create_time=" + create_time
				+ "]";
	}
	
}
