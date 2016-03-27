package com.ddcb.model;

import java.sql.Timestamp;

public class QuestionModel {

	private Long id;
	private String open_id;
	private String question;
	private Integer click_like;
	private Long course_id;
	private String headimgurl;
	private String user_nickname;
	private String create_time_readable;
	private Integer current_click_like;
	private Timestamp create_time;
	
	public QuestionModel() {}

	public QuestionModel(Long id, String open_id, String question, Integer click_like, Long course_id,
			String user_nickname, String headimgurl, String create_time_readable, Integer current_click_like, Timestamp create_time) {
		super();
		this.id = id;
		this.open_id = open_id;
		this.question = question;
		this.click_like = click_like;
		this.course_id = course_id;
		this.user_nickname = user_nickname;
		this.headimgurl = headimgurl;
		this.create_time_readable = create_time_readable;
		this.create_time = create_time;
		this.current_click_like = current_click_like;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getClick_like() {
		return click_like;
	}

	public void setClick_like(Integer click_like) {
		this.click_like = click_like;
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getCreate_time_readable() {
		return create_time_readable;
	}

	public void setCreate_time_readable(String create_time_readable) {
		this.create_time_readable = create_time_readable;
	}

	public Integer getCurrent_click_like() {
		return current_click_like;
	}

	public void setCurrent_click_like(Integer current_click_like) {
		this.current_click_like = current_click_like;
	}
	
}
