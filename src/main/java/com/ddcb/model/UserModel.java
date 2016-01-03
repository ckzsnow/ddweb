package com.ddcb.model;

import java.sql.Timestamp;

public class UserModel {

	private String user_id;
	private String user_pwd;
	private Integer user_type;
	private Timestamp create_time;
	
	public UserModel() {}
	
	public UserModel(String user_id, String user_pwd, Integer user_type, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.user_type = user_type;
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserModel [user_id=" + user_id + ", user_pwd=" + user_pwd + ", user_type=" + user_type
				+ ", create_time=" + create_time + "]";
	}
		
}
