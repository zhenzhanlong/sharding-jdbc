package com.complex.practice.http.type;

/**
 * http contentType
 * 
 * @author dwpeng
 */
public enum ContentType {

	JSON("application/json;charset=utf-8"), XML("application/xml;charset=utf-8"),XML_GBK("application/xml;charset=GBK");

	private String contentType;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private ContentType(String contentType) {
		this.contentType = contentType;
	}

}
