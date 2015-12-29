package com.ddcb.model;

import java.sql.Timestamp;

public class UserForwardModel {

	private String open_id;
	private String screenshot;
	private Timestamp create_time;
	
	public UserForwardModel(String open_id, String screenshot, Timestamp create_time) {
		super();
		this.open_id = open_id;
		this.screenshot = screenshot;
		this.create_time = create_time;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserForwardModel [open_id=" + open_id + ", screenshot=" + screenshot + ", create_time=" + create_time
				+ "]";
	}
		
}
