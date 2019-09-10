package com.complex.practice.type.consumer;
/**
 * 用户属性
 * @author lenovo
 *
 */
public enum ConsumerType {
	PLAT_MANAGER("平台管理员"),
	PERSON("个人"),
	COMPANY_USER("公司用户");
	
	/**说明*/
	private String description;
	
	private ConsumerType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
