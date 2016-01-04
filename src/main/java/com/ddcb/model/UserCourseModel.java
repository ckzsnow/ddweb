package com.ddcb.model;

import java.sql.Timestamp;

public class UserCourseModel {

	private String user_id;
	private Integer pay_status;
	private Integer forward_status;
	private Long course_id;
	private String upload_time;
	private String screenshot;
	private Timestamp create_time;
	
	public UserCourseModel() {}
	
	public UserCourseModel(String user_id, Integer pay_status, Integer forward_status, Long course_id,
			String upload_time, String screenshot, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.pay_status = pay_status;
		this.forward_status = forward_status;
		this.course_id = course_id;
		this.upload_time = upload_time;
		this.screenshot = screenshot;
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public Integer getForward_status() {
		return forward_status;
	}

	public void setForward_status(Integer forward_status) {
		this.forward_status = forward_status;
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

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	@Override
	public String toString() {
		return "UserCourseModel [user_id=" + user_id + ", pay_status=" + pay_status + ", forward_status="
				+ forward_status + ", course_id=" + course_id + ", upload_time=" + upload_time + ", screenshot="
				+ screenshot + ", create_time=" + create_time + "]";
	}
		
}
