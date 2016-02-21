package com.ddcb.model;

import java.sql.Timestamp;

public class UserStudyRecordModel {

	private String user_id;
	private Long course_id;
	private Integer course_type;
	private Integer pay_status;
	private String name;
	private String teacher;
	private String image;
	private Timestamp create_time;
	
	public UserStudyRecordModel() {}

	public UserStudyRecordModel(String user_id, Long course_id, Integer course_type, Integer pay_status, String name,
			String teacher, String image, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.course_id = course_id;
		this.course_type = course_type;
		this.pay_status = pay_status;
		this.name = name;
		this.teacher = teacher;
		this.image = image;
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

	public Integer getCourse_type() {
		return course_type;
	}

	public void setCourse_type(Integer course_type) {
		this.course_type = course_type;
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserStudyRecordModel [user_id=" + user_id + ", course_id=" + course_id + ", course_type=" + course_type
				+ ", pay_status=" + pay_status + ", name=" + name + ", teacher=" + teacher + ", image=" + image
				+ ", create_time=" + create_time + "]";
	}

}
