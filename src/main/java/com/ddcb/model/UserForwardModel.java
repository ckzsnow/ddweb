package com.ddcb.model;

import java.sql.Timestamp;

public class UserForwardModel {

	private String user_id;
	private Long course_id;
	private String screenshot;
	private Timestamp create_time;
	
	public UserForwardModel() {}
	
	public UserForwardModel(String user_id, Long course_id, String screenshot, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.course_id = course_id;
		this.screenshot = screenshot;
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

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserForwardModel [user_id=" + user_id + ", course_id=" + course_id + ", screenshot=" + screenshot
				+ ", create_time=" + create_time + "]";
	}
		
}
