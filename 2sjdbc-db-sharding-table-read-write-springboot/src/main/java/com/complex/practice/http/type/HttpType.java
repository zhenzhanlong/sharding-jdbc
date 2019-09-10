package com.complex.practice.http.type;

/**
 * http协议
 * 
 * @author dwpeng
 */
public enum HttpType {

	HTTP("http"), HTTPS("https");

	private String httptype;

	public String getHttptype() {
		return httptype;
	}

	public void setHttptype(String httptype) {
		this.httptype = httptype;
	}

	private HttpType(String httptype) {
		this.httptype = httptype;
	}
}
