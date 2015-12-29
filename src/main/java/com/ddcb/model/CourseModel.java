package com.ddcb.model;

import java.sql.Timestamp;

public class CourseModel {

	private Long id;
	private String name;
	private String course_abstract;
	private String teacher;
	private String image;
	private Timestamp create_time;
	
	public CourseModel(Long id, String name, String course_abstract, String teacher, String image,
			Timestamp create_time) {
		super();
		this.id = id;
		this.name = name;
		this.course_abstract = course_abstract;
		this.teacher = teacher;
		this.image = image;
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

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "CourseModel [id=" + id + ", name=" + name + ", course_abstract=" + course_abstract + ", teacher="
				+ teacher + ", image=" + image + ", create_time=" + create_time + "]";
	}
}
