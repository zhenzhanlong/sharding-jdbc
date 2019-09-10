package com.complex.practice.type.excel;
/**
 * 支付密码状态数据字段
 * @author lenovo
 *
 */
public enum ExcelDataStatusType {
	NEW_IMPORT("新增"),
	SPLIT_SUCCESS("拆分成功"),
	SPLIT_FAIL("拆分失败");
	/**说明*/
	private String description;

	private ExcelDataStatusType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
