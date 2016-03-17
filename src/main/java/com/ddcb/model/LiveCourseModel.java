package com.ddcb.model;

import java.sql.Timestamp;

public class LiveCourseModel {

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
	private String price;
	private String hasCollection;
	private String screenshot;
	private Integer donate_pay_status;
	
	private Integer select_status;
	private Integer pay_status;
	private Integer forward_status;
		
	public LiveCourseModel(){}
	
	public LiveCourseModel(Long id, String name, String course_abstract, String teacher, String image,
			Timestamp course_date, String course_date_readable, String course_time, 
			String course_length, Timestamp create_time, Integer courseType,
			String courseField, String courseIndustry, String courseCompetency, String price, String hasCollection,
			Integer payStatus, String screenshot, Integer donate_pay_status) {
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
		this.hasCollection = hasCollection;
		this.pay_status = payStatus;
		this.screenshot = screenshot;
		this.donate_pay_status = donate_pay_status;
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

	public String getHasCollection() {
		return hasCollection;
	}

	public void setHasCollection(String hasCollection) {
		this.hasCollection = hasCollection;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public Integer getDonate_pay_status() {
		return donate_pay_status;
	}

	public void setDonate_pay_status(Integer donate_pay_status) {
		this.donate_pay_status = donate_pay_status;
	}

	@Override
	public String toString() {
		return "LiveCourseModel [id=" + id + ", name=" + name + ", course_abstract=" + course_abstract + ", teacher="
				+ teacher + ", image=" + image + ", course_date=" + course_date + ", course_date_readable="
				+ course_date_readable + ", course_time=" + course_time + ", course_length=" + course_length
				+ ", create_time=" + create_time + ", courseType=" + courseType + ", courseField=" + courseField
				+ ", courseIndustry=" + courseIndustry + ", courseCompetency=" + courseCompetency + ", price=" + price
				+ ", hasCollection=" + hasCollection + ", screenshot=" + screenshot + ", donate_pay_status="
				+ donate_pay_status + ", select_status=" + select_status + ", pay_status=" + pay_status
				+ ", forward_status=" + forward_status + "]";
	}
}
