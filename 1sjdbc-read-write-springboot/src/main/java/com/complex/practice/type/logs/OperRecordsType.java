package com.complex.practice.type.logs;

/**
 * 日志操作类型
 * 
 * @author lenovo
 *
 */
public enum OperRecordsType {
	// 系统编号 ：后台管理，企业管理，个人
	SELEDT("查询"), 
	ADD("新增"), 
	UPDATE("更新"), 
	DELETE("删除"), 
	EXCEL_ERPORT("Excel数据导出"), 
	LOGINON("用户登录"), 
	LOGINOUT("用户退出"), 
	DELETE_ADD("先删后增");
	/** 说明 ***/
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private OperRecordsType(String description) {
		this.description = description;
	}

}
