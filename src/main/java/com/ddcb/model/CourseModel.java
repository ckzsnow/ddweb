package com.ddcb.model;

import java.sql.Timestamp;

public class CourseModel {

	private Long id;
	private String name;
	private String course_abstract;
	private String teacher;
	private String image;
	private Timestamp course_date;
	private String course_time;
	private String course_length;
	private Timestamp create_time;
	
	private Integer select_status;
	private Integer pay_status;
	private Integer forward_status;
		
	public CourseModel(){}
	
	public CourseModel(Long id, String name, String course_abstract, String teacher, String image,
			Timestamp course_date, String course_time, String course_length, Timestamp create_time) {
		super();
		this.id = id;
		this.name = name;
		this.course_abstract = course_abstract;
		this.teacher = teacher;
		this.image = image;
		this.course_date = course_date;
		this.course_time = course_time;
		this.course_length = course_length;
		this.create_time = create_time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse_abstract() {
		return course_abstract;
	}

	public void setCourse_abstract(String course_abstract) {
		this.course_abstract = course_abstract;
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

	public Timestamp getCourse_date() {
		return course_date;
	}

	public void setCourse_date(Timestamp course_date) {
		this.course_date = course_date;
	}

	public String getCourse_time() {
		return course_time;
	}

	public void setCourse_time(String course_time) {
		this.course_time = course_time;
	}

	public String getCourse_length() {
		return course_length;
	}

	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Integer getSelect_status() {
		return select_status;
	}

	public void setSelect_status(Integer select_status) {
		this.select_status = select_status;
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

	@Override
	public String toString() {
		return "CourseModel [id=" + id + ", name=" + name + ", course_abstract=" + course_abstract + ", teacher="
				+ teacher + ", image=" + image + ", course_date=" + course_date + ", course_time=" + course_time
				+ ", course_length=" + course_length + ", create_time=" + create_time + "]";
	}
}
