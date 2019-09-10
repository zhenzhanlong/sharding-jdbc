package com.complex.practice.http;

public class HttpHostConfig {

	// 每个主机的并发最多数
	private int maxperroute;
	/**
	 * 主机IP地址
	 */
	private String hostip;

	public int getMaxperroute() {
		return maxperroute;
	}

	public void setMaxperroute(int maxperroute) {
		this.maxperroute = maxperroute;
	}

	public String getHostip() {
		return hostip;
	}

	public void setHostip(String hostip) {
		this.hostip = hostip;
	}

}
