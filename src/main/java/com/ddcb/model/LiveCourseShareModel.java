package com.ddcb.model;

public class LiveCourseShareModel {

	private Long id;
	private String image;
	private String link;
	private String title;
	private String weekDay;
	private Long courseId;
	private String courseName;
	
	public LiveCourseShareModel() {}

	public LiveCourseShareModel(Long id, String image, String link, String title, String weekDay, Long courseId,
			String courseName) {
		super();
		this.id = id;
		this.image = image;
		this.link = link;
		this.title = title;
		this.weekDay = weekDay;
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "LiveCourseShareModel [id=" + id + ", image=" + image + ", link=" + link + ", title=" + title
				+ ", weekDay=" + weekDay + ", courseId=" + courseId + ", courseName=" + courseName + "]";
	}
	
}
