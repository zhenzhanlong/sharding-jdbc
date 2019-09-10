package com.complex.practice.type.permission;

/**
 * 权限关系类型
 * 
 * @author lenovo
 *
 */
public enum PermissionRelationType {
	COMPANY_USER_ROLE("公司用户与角色关系"), 
	COMPANY_ROLE_MENU("公司角色关系与模块关系"),
	COMPANY_ROLE_BUTTON("公司角色与按钮关系"),
	PLAT_USER_ROLE("平台用户与角色关系"), 
	PLAT_ROLE_MENU("平台角色关系与模块关系"),
	PLAT_ROLE_BUTTON("平台角色与按钮关系");

	/** 说明 ***/
	private String description;

	private PermissionRelationType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
