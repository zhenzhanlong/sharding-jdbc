package com.complex.practice.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取消息配置工具类
 * 
 * @author lxh
 */
public class MsgCodeUtil {
	private static Properties properties;

	static {
		properties = new Properties();

		try (InputStreamReader isr = new InputStreamReader(
				MsgCodeUtil.class.getClassLoader().getResourceAsStream("errorcode.properties"), "utf-8")) {
			properties.load(isr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过key获取消息
	 * 
	 * @param key
	 * @return
	 */
	public static String getMsg(String key) {
		if (key == null) {
			return key;
		}
		return properties.getProperty(key).split(":").length == 2 ? properties.getProperty(key).split(":")[1] : null;
	}

	/**
	 * 通过key值获取消息并且加上扩展内容
	 * 
	 * @param key
	 * @param extMsg
	 * @return
	 */
	public static String getMsg(String key, String extMsg) {
		if (key == null) {
			return key;
		}
		return properties.getProperty(key).split(":")[1] + "." + extMsg;
	}

	/**
	 * 通过key获取code
	 * 
	 * @param errorKey
	 * @return
	 */
	public static String getMsgCode(String key) {
		if (key == null) {
			return key;
		}
		return properties.getProperty(key).split(":")[0];
	}
}
