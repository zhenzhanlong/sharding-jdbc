package com.complex.practice.type.excel;

public enum AuditStatus {
	
	APPLY_STATUS_NO("未申请"),
	APPLY_STATUS_YES("已申请"),
	APPLY_STATUS_MIDDLE("申请中"),
	APPLY_STATUS_FINISH("审核完成"),
	AUDIT_STATUS_NO("未审核"),
		//申请状态
	AUDIT_STATUS_APPLICATION("审核中"),
	
	AUDIT_STATUS_FINISH("审核通过"),
		//审核成功
	AUDIT_STATUS_SUCCEED("同意"),
		//审核失败
	AUDIT_STATUS_FAILURE("不同意");
	
	/**说明*/
	private String description;
	
	private AuditStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
