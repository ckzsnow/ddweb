package com.ddcb.model;

public class LiveClassApplyModel {

	private Long id;
	private String name;
	private String teacher;
	private Integer total;
	
	public LiveClassApplyModel(){}
	
	public LiveClassApplyModel(Long id, String name, String teacher, Integer total) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
