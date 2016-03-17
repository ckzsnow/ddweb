package com.ddcb.model;

import java.sql.Timestamp;

public class UserLiveCoursePayModel {

	private String user_id;
	private Integer pay_status;
	private Integer forward_status;
	private Long course_id;
	private String tradeNo;
	private Timestamp create_time;
	
	public UserLiveCoursePayModel() {}
	
	public UserLiveCoursePayModel(String user_id, String tradeNo, Integer pay_status, Integer forward_status, Long course_id,
			Timestamp create_time) {
		super();
		this.user_id = user_id;
		this.tradeNo = tradeNo;
		this.pay_status = pay_status;
		this.forward_status = forward_status;
		this.course_id = course_id;
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	
	@Override
	public String toString() {
		return "UserLiveCoursePayModel [user_id=" + user_id + ", pay_status=" + pay_status + ", forward_status="
				+ forward_status + ", course_id=" + course_id + ", tradeNo=" + tradeNo + ", create_time=" + create_time
				+ "]";
	}
		
}
