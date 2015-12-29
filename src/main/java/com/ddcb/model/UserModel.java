package com.ddcb.model;

import java.sql.Timestamp;

public class UserModel {

	private String open_id;
	private Integer pay_status;
	private Integer forward_status;
	private Timestamp create_time;
	
	public UserModel(String open_id, Integer pay_status, Integer forward_status, Timestamp create_time) {
		super();
		this.open_id = open_id;
		this.pay_status = pay_status;
		this.forward_status = forward_status;
		this.create_time = create_time;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
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

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "UserModel [open_id=" + open_id + ", pay_status=" + pay_status + ", forward_status=" + forward_status
				+ ", create_time=" + create_time + "]";
	}
		
}
