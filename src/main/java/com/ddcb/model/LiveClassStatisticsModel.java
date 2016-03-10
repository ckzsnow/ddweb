package com.ddcb.model;

import java.sql.Timestamp;

public class LiveClassStatisticsModel {

	private String open_id;
	private long course_id;
	private String play_status;
	private Timestamp create_time;
	
	public LiveClassStatisticsModel() {}

	public LiveClassStatisticsModel(String open_id, long course_id, String play_status, Timestamp create_time) {
		super();
		this.open_id = open_id;
		this.course_id = course_id;
		this.play_status = play_status;
		this.create_time = create_time;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(long course_id) {
		this.course_id = course_id;
	}

	public String getPlay_status() {
		return play_status;
	}

	public void setPlay_status(String play_status) {
		this.play_status = play_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "LiveClassStatisticsModel [open_id=" + open_id + ", course_id=" + course_id + ", play_status="
				+ play_status + ", create_time=" + create_time + "]";
	}
	
}
