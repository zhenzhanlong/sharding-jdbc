package com.complex.practice.type;
/**
 * 位置调整枚举
 * @author lenovo
 *
 */
public enum PositionAdjustType {
	UP("位置上移"),
	DOWN("位置下移"),
	SET_UP("设置排序号"),
	EXCHANGE("位置交换");
	
	/**说明*/
	private String description;
	
	private PositionAdjustType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
