package com.complex.practice.type.logs;

/**
 * 日志操作类型
 * 
 * @author lenovo
 *
 */
public enum ModularType {
	// 系统编号 ：后台管理，企业管理，个人
	MENU("模块信息"), BUTTON("按钮信息"), ROLE("角色信息"), SYS_USER("系统用户");
	/** 说明 ***/
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private ModularType(String description) {
		this.description = description;
	}

}
