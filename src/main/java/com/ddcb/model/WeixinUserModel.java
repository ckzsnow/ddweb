package com.ddcb.model;

import java.sql.Timestamp;

public class WeixinUserModel {

	private String user_id;
	private Integer user_type;
	private String trade_no;
	private Integer pay_status;
	private Timestamp expiration_time;
	private Timestamp create_time;
	
	public WeixinUserModel() {}

	public WeixinUserModel(String user_id, Integer user_type, String trade_no, Integer pay_status,
			Timestamp expiration_time, Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.trade_no = trade_no;
		this.pay_status = pay_status;
		this.expiration_time = expiration_time;
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public Timestamp getExpiration_time() {
		return expiration_time;
	}

	public void setExpiration_time(Timestamp expiration_time) {
		this.expiration_time = expiration_time;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "WeixinUserModel [user_id=" + user_id + ", user_type=" + user_type + ", trade_no=" + trade_no
				+ ", pay_status=" + pay_status + ", expiration_time=" + expiration_time + ", create_time=" + create_time
				+ "]";
	}
	
}
