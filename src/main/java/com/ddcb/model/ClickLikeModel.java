package com.ddcb.model;

import java.sql.Timestamp;

public class ClickLikeModel {

	private Long id;
	private Long question_id;
	private String open_id;
	private Integer like;
	private Timestamp create_time;
	
	public ClickLikeModel() {}

	public ClickLikeModel(Long id, Long question_id, String open_id, Integer like, Timestamp create_time) {
		super();
		this.id = id;
		this.question_id = question_id;
		this.open_id = open_id;
		this.like = like;
		this.create_time = create_time;
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

	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Long getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}
	
}
