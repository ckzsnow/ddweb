package com.ddcb.model;

import java.sql.Timestamp;

public class UserOpenIdDetailModel {

	private String open_id;
	private String user_nickname;
	private String user_sex;
	private String user_province;
	private String user_city;
	private String headimgurl;
	private String user_id;
	private Integer user_type;
	private String trade_no;
	private Integer pay_status;
	private Timestamp expiration_time;
	private Timestamp create_time;
		
	public UserOpenIdDetailModel() {}

	public UserOpenIdDetailModel(String open_id, String user_nickname, String user_sex, String user_province,
			String user_city, String headimgurl, String user_id, Integer user_type, String trade_no, Integer pay_status,
			Timestamp expiration_time, Timestamp create_time) {
		super();
		this.open_id = open_id;
		this.user_nickname = user_nickname;
		this.user_sex = user_sex;
		this.user_province = user_province;
		this.user_city = user_city;
		this.headimgurl = headimgurl;
		this.user_id = user_id;
		this.user_type = user_type;
		this.trade_no = trade_no;
		this.pay_status = pay_status;
		this.expiration_time = expiration_time;
		this.create_time = create_time;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_province() {
		return user_province;
	}

	public void setUser_province(String user_province) {
		this.user_province = user_province;
	}

	public String getUser_city() {
		return user_city;
	}

	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
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
		return "UserOpenIdDetailModel [open_id=" + open_id + ", user_nickname=" + user_nickname + ", user_sex="
				+ user_sex + ", user_province=" + user_province + ", user_city=" + user_city + ", headimgurl="
				+ headimgurl + ", user_id=" + user_id + ", user_type=" + user_type + ", trade_no=" + trade_no
				+ ", pay_status=" + pay_status + ", expiration_time=" + expiration_time + ", create_time=" + create_time
				+ "]";
	}
	
}
