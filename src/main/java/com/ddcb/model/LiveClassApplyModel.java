package com.ddcb.model;

public class LiveClassApplyModel {

	private Long id;
	private String name;
	private Integer total;
	
	public LiveClassApplyModel(){}
	
	public LiveClassApplyModel(Long id, String name, Integer total) {
		super();
		this.id = id;
		this.name = name;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
