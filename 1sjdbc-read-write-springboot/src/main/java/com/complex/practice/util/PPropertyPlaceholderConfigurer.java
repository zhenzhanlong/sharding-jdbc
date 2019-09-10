package com.complex.practice.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 重写spring属性文件工具类,以便以后加密使用
 * 
 * @author dwpeng
 */
public class PPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	public volatile static Properties props;

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {

		return super.convertProperty(propertyName, propertyValue);
	}

	public static Properties getProps() {
		return props;
	}

	private static void setProps(Properties props) {
		PPropertyPlaceholderConfigurer.props = props;
	}

	@Override
	protected void convertProperties(Properties props) {
		super.convertProperties(props);
		setProps(props);
	}

}
