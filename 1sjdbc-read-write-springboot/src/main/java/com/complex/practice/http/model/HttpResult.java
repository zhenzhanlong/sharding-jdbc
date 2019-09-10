package com.complex.practice.http.model;

public class HttpResult {

	/**
	 * http请求状态
	 */
	private int status;
	/**
	 * http请求返回结果
	 */
	private String httpresult;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHttpresult() {
		return httpresult;
	}

	public void setHttpresult(String httpresult) {
		this.httpresult = httpresult;
	}

	@Override
	public String toString() {
		return "HttpResult [status=" + status + ", httpresult=" + httpresult + "]";
	}

}
