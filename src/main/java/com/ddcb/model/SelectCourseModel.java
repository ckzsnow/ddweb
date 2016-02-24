package com.ddcb.model;

public class SelectCourseModel {

	private Long id;
	private String name;
	private String image;
	private String course_length;
	private Integer people_count;
			
	public SelectCourseModel(){}

	public SelectCourseModel(Long id, String name, String image, String course_length, Integer people_count) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.course_length = course_length;
		this.people_count = people_count;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCourse_length() {
		return course_length;
	}

	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}

	public Integer getPeople_count() {
		return people_count;
	}

	public void setPeople_count(Integer people_count) {
		this.people_count = people_count;
	}

	@Override
	public String toString() {
		return "SelectCourseModel [id=" + id + ", name=" + name + ", image=" + image + ", course_length="
				+ course_length + ", people_count=" + people_count + "]";
	}
	
}
