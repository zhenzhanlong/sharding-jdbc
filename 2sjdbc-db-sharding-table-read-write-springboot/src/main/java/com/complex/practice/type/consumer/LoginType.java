package com.complex.practice.type.consumer;
/**
 * 登陆方式
 * @author lenovo
 *
 */
public enum LoginType {
	PHONE("手机"),
	EMAIL("邮箱"),
	QQ("是"),
	WEIXIN("否");
	/**说明*/
	private String description;
	
	private LoginType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
