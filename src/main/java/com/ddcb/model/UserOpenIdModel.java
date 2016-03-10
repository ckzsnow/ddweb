package com.ddcb.model;

public class UserOpenIdModel {

	private String open_id;
	private String user_nickname;
	private String user_sex;
	private String user_province;
	private String user_city;
	private String headimgurl;
		
	public UserOpenIdModel() {}

	public UserOpenIdModel(String open_id, String user_nickname, String user_sex, String user_province,
			String user_city, String headimgurl) {
		super();
		this.open_id = open_id;
		this.user_nickname = user_nickname;
		this.user_sex = user_sex;
		this.user_province = user_province;
		this.user_city = user_city;
		this.headimgurl = headimgurl;
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

	@Override
	public String toString() {
		return "UserOpenIdModel [open_id=" + open_id + ", user_nickname=" + user_nickname + ", user_sex=" + user_sex
				+ ", user_province=" + user_province + ", user_city=" + user_city + ", headimgurl=" + headimgurl + "]";
	}		
	
}
