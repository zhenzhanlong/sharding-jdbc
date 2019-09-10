package com.complex.practice.type.permission;

/**
 * url调用后台类型
 * 
 * @author lenovo
 *
 */
public enum MRequestType {
	AXAJ("ajax请求"), 
	MODEL_AND_VIEW("界面跳转");

	/** 说明 ***/
	private String description;

	private MRequestType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
