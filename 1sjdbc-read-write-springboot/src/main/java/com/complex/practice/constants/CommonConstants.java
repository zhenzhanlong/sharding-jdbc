package com.complex.practice.constants;

import com.complex.practice.util.PPropertyPlaceholderConfigurer;

/**
 * 系统常量类
 * 
 * @author lenovo
 *
 */
public class CommonConstants {
	private CommonConstants() {
		super();
	}
	/** http超时设置 */
	public static final Integer HTTP_TIME_OUT = 50000;
	/** http请求编码 **/
	public static final String CODING = "UTF-8";
	/** 验证码有效时间10分钟 **/
	public static final int SECOND_TEN_MINUTE = 600;
	/** session超时设置 */
	public static final Integer SESSION_TIME_SET_UP = 3000;
	/** token超时设置 */
	public static final Integer LOGIN_TIME_SET_UP = 604800;

	/** 数字 0 **/
	public static final int ZERO_NUM=0;
	
	/** 层级 1 **/
	public static final int LEVEL_ONE=1;
	/** 层级 2 **/
	public static final int LEVEL_TWO=2;
	/** 层级 3 **/
	public static final int LEVEL_THREE=3;
	/** 层级 4 **/
	public static final int LEVEL_FOUR=4;
	
	/** OSS 内网配置 **/
	public static final String END_POINT = PPropertyPlaceholderConfigurer.props.getProperty("endpoint");
	/** OSS 外网配置 **/
	public static final String END_POINT_OUT = PPropertyPlaceholderConfigurer.props.getProperty("endPointOut");
	/** OSS 图片根目录 **/
	public static final String OSS_ROUTE = PPropertyPlaceholderConfigurer.props.getProperty("oss_route");
	/** OSS 模式 dev 开发、测试。online 线上 **/
	public static final String OSS_MODEL = PPropertyPlaceholderConfigurer.props.getProperty("oss_model");

	/** 数字0 **/
	public static final int NUM_ZERO = 0;
	/** 数字1 **/
	public static final int NUM_ONE = 1;
	/** 数字2 **/
	public static final int NUM_TWO = 2;
	
	
	/** 状态： 禁用 **/
	public static final int STATE_DISABLE = 0;
	/** 状态： 启用 **/
	public static final int STATE_ENABLE = 1;
	
	
	/** 商品状态：停用、，新增 **/
	public static final Integer STATUS_0 = 0;
	/** 商品状态：启用等**/
	public static final Integer STATUS_1 = 1;
	/** 商品状态：待上架 **/
	public static final Integer STATUS_2 = 2;
	/** 商品状态：在售 **/
	public static final Integer STATUS_3 = 3;
	/** 商品状态：已下架 **/
	public static final Integer STATUS_4 = 4;
	/** 商品状态：强制下架 **/
	public static final Integer STATUS_5 = 5;
}
