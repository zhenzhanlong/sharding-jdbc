package com.complex.practice.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户密码加密算法
 * 
 * @author lenovo
 *
 */
public class PasswordUtil {
	private static final String SEPCIAL_CONNECT_CHAR = "#";

	public static void main(String[] args) {
	}

	/**
	 * 用户密码数据加密
	 * 
	 * @return
	 */
	public static String encodePassword(Long userId, String password) {
		// 参数不能为空
		if (null == userId || StringUtils.isBlank(password)) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		str.append(password).append(SEPCIAL_CONNECT_CHAR).append(userId);
		return str.toString();
	}

}
