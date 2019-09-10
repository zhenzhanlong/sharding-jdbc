package com.complex.practice.type.excel;

public enum VerifyStatusType {

	// 未认证
	VERIFY_STATUS_UNVERIFIED("未认验证"),
	// 验证成功
	VERIFY_STATUS_SUCCEED("验证成功"),
	// 验证失败
	VERIFY_STATUS_FAILURE("验证失败");

	/** 说明 */
	private String description;

	private VerifyStatusType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
