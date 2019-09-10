package com.complex.practice.type.state;

/**
 * 支付密码状态数据字段
 * 
 * @author lenovo
 *
 */
public enum DataStatusType {
	NEW_ADD("新增"), 
	NOT_SET("未设置"), 
	NETWORK_REGISTRY("线上注册"), 
	VALIDATE_NOT_YET("未认证"), 
	VALIDATE_FAILURE("认证失败"), 
	OPENED("开户"), 
	NOT_OPEN("未开户"), 
	OPENING("开户中"),
	OPEN_FAIL("开户失败"), 
	AVAILABLE("启用"), 
	DELETE("删除"), 
	LOCKING("锁定"), 
	LEAVE("离职"), 
	FINISH("完成"), 
	ARRIVE("到账"), 
	DISABLE("停用");

	/** 说明 */
	private String description;

	private DataStatusType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
