package com.complex.practice.util;

/**
 * 根据表名生成主键
 * 
 * @author dwpeng
 *
 */
public enum PrimarykeyEnum {
	/**
	 * 项目信息
	 */
	SX_T_E_PROJECT("SX_T_E_PROJECT","0","2147483647"),
	/**
	 * (唯一联合主键)商铺id、公司用户id、个人用户id Unique Union
	 */
	SX_T_UNIQUE_UNION_PRYMARY_KEY("SX_T_E_PERSON_INFO","200","2147483647"),
	/**
	 * 用户浏览记录 2147483647 
	 */
	SX_T_E_BROWSE_RECORDS("SX_T_E_BROWSE_RECORDS","1","2147483647"),
	/**
	 * 系统用户、公司用户、个人用户联合主键，说不定商铺等可能还需要这个 2147483647 
	 */
	SX_T_E_UNIFY_CONSUMER("SX_T_E_UNIFY_CONSUMER","1","2147483647"),
	/**
	 * 公司用户、个人用户联合主键，说不定商铺等可能还需要这个 2147483647
	 */
	SX_T_E_STORE("SX_T_E_STORE","1","2147483647"),
	/**
	 * 商家服务id
	 */
	SX_T_E_SERVICE("SX_T_E_SERVICE","16","2147483647"),
	/**
	 * 操作日志
	 */
	SX_T_E_OPER_LOG("SX_T_E_OPER_LOG","1","99999999999"),
	/**
	 * 评价信息表 evaluate
	 */
	SX_T_E_EVALUATE("SX_T_E_EVALUATE","1","2147483647"),
	/**
	 * 交易信息表 trade
	 */
	SX_T_E_TRADE("SX_T_E_TRADE","1","2147483647"),
	/**
	 * 交易信息表 trade
	 */
	SX_T_E_TRADE_NUM("SX_T_E_TRADE_NUM","100000","999999"),
	/**
	 * 服务信息表 服务编号
	 */
	SX_T_E_SERVICE_NUM("SX_T_E_TRADE_NUM","100000","999999"),
	/**
	 * 订单 num
	 */
	SX_T_E_ORDER_NUM("SX_T_E_ORDER_NUM","100000","999999"),
	/**
	 * B2B项目编号
	 */
	SX_T_E_B2B_PROJECT_CODE("SX_T_E_B2B_PROJECT_CODE","100000","999999"),
	/**
	 * B2C项目编号
	 */
	SX_T_E_B2C_PROJECT_CODE("SX_T_E_B2C_PROJECT_CODE","100000","999999"),
	/**
	 * C2C项目编号
	 */
	SX_T_E_C2C_PROJECT_CODE("SX_T_E_C2C_PROJECT_CODE","100000","999999"),
	
	/**
	 * 项目订单 num
	 */
	SX_T_E_PROJECT_PAY_NUM("SX_T_E_PROJECT_PAY_NUM","100000","999999"),
	/**
	 * 项目订单 num
	 */
	SX_T_E_PROJECT_ORDER_NUM("SX_T_E_PROJECT_ORDER_NUM","100000","999999");
	private PrimarykeyEnum(String tablename, String intmum, String maxnum) {
		this.tablename = tablename;
		this.intmum = intmum;
		this.maxnum = maxnum;
	}
	/**
	 * 序列的名称
	 */
	private String tablename;

	/**
	 * 序列开始值
	 */
	private String intmum;
	/**
	 * 序列最大值
	 */
	private String maxnum;

	public String getIntmum() {
		return intmum;
	}

	public String getMaxnum() {
		return maxnum;
	}
	public String getTablename() {
		return tablename;
	}

}
