package com.complex.practice.type.consumer;

/**
 * 证件类型
 */
public enum PaperType {

	IDENTITYCARD("1", "个人身份证"), SOLDIERCARD("2", "士兵证"), OFFICER("3", "军官证"), POLICE("4", "警察证"), HOUSEHOLD("5", "户口本"), TEMPORARY("6", "临时证件"), PASSPORT("7", "护照"), OTHER("8", "其他"),
	LICENSE("16","营业执照号"),FOREIGNPASSPORT("19","外国护照"),ORGANIZATIONCODECERTIFICATE("17","组织机构代码证");

	private String value;
	private String desc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private PaperType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
}
