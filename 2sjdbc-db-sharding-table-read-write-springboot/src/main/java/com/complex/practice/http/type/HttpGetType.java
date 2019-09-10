package com.complex.practice.http.type;

/**
 * http sent type
 * 
 * @author dwpeng
 */
public enum HttpGetType {

	POST("POST"), GET("GET");

	private String httptype;

	public String getHttptype() {
		return httptype;
	}

	public void setHttptype(String httptype) {
		this.httptype = httptype;
	}

	private HttpGetType(String httptype) {

		this.httptype = httptype;
	}

}
