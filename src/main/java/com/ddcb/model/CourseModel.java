package com.ddcb.model;

import java.sql.Timestamp;

public class CourseModel {

	private Long id;
	private String name;
	private String course_abstract;
	private String teacher;
	private String image;
	private Timestamp course_date;
	private String course_date_readable;
	private String course_time;
	private String course_length;
	private Timestamp create_time;
	private Integer courseType;
	private String courseField;
	private String courseIndustry;
	private String courseCompetency;
	private String courseGrade;
	private String price;
	private Integer people_count;
	private Integer study_people_count;
	private Integer hasCollection;
	private Long parentId;
	
	private Integer select_status;
	private Integer pay_status;
	private Integer forward_status;
		
	public CourseModel(){}
	
	public CourseModel(Long id, String name, String course_abstract, String teacher, String image,
			Timestamp course_date, String course_date_readable, String course_time, 
			String course_length, Timestamp create_time, Integer courseType,
			String courseField, String courseIndustry, String courseCompetency, 
			String price, Integer people_count, Integer study_people_count, Integer hasCollection,
			Long parentId, String courseGrade) {
		super();
		this.id = id;
		this.name = name;
		this.course_abstract = course_abstract;
		this.teacher = teacher;
		this.image = image;
		this.course_date = course_date;
		this.course_date_readable = course_date_readable;
		this.course_time = course_time;
		this.course_length = course_length;
		this.create_time = create_time;
		this.courseType = courseType;
		this.courseField = courseField;
		this.courseIndustry = courseIndustry;
		this.courseCompetency = courseCompetency;
		this.price = price;
		this.people_count = people_count;
		this.study_people_count = study_people_count;
		this.hasCollection = hasCollection;
		this.parentId = parentId;
		this.courseGrade = courseGrade;
	}

	public Integer getHasCollection() {
		return hasCollection;
	}

	public void setHasCollection(Integer hasCollection) {
		this.hasCollection = hasCollection;
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

	public String getCourse_date_readable() {
		return course_date_readable;
	}

	public void setCourse_date_readable(String course_date_readable) {
		this.course_date_readable = course_date_readable;
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

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	public String getCourseField() {
		return courseField;
	}

	public void setCourseField(String courseField) {
		this.courseField = courseField;
	}

	public String getCourseIndustry() {
		return courseIndustry;
	}

	public void setCourseIndustry(String courseIndustry) {
		this.courseIndustry = courseIndustry;
	}

	public String getCourseCompetency() {
		return courseCompetency;
	}

	public void setCourseCompetency(String courseCompetency) {
		this.courseCompetency = courseCompetency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getPeople_count() {
		return people_count;
	}

	public void setPeople_count(Integer people_count) {
		this.people_count = people_count;
	}

	public Integer getStudy_people_count() {
		return study_people_count;
	}

	public void setStudy_people_count(Integer study_people_count) {
		this.study_people_count = study_people_count;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCourseGrade() {
		return courseGrade;
	}

	public void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}

	@Override
	public String toString() {
		return "CourseModel [id=" + id + ", name=" + name + ", course_abstract=" + course_abstract + ", teacher="
				+ teacher + ", image=" + image + ", course_date=" + course_date + ", course_date_readable="
				+ course_date_readable + ", course_time=" + course_time + ", course_length=" + course_length
				+ ", create_time=" + create_time + ", courseType=" + courseType + ", courseField=" + courseField
				+ ", courseIndustry=" + courseIndustry + ", courseCompetency=" + courseCompetency + ", courseGrade="
				+ courseGrade + ", price=" + price + ", people_count=" + people_count + ", study_people_count="
				+ study_people_count + ", hasCollection=" + hasCollection + ", parentId=" + parentId
				+ ", select_status=" + select_status + ", pay_status=" + pay_status + ", forward_status="
				+ forward_status + "]";
	}
}
