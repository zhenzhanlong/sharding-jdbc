package com.complex.practice.http.model;

import com.complex.practice.http.type.ContentType;
import com.complex.practice.http.type.HttpGetType;
import com.complex.practice.http.type.HttpType;

public class URLModel {

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 用户�?
	 */
	private String username;
	/**
	 * 密码
	 */
	private String passwd;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 起始位置
	 */
	private int start = 0;
	/**
	 * 返回的记录数
	 */
	private int size = 10000;

	/**
	 * http get,post
	 */
	private HttpGetType httpgetype;

	/**
	 * httptype http,https
	 */
	private HttpType httptype;

	/**
	 * ContentType json,xml
	 */
	private ContentType contenttype;

	/**
	 * 包的名称
	 */
	private String packagename;

	private String scheme;
	/**
	 * 主机ip
	 */
	private String host;

	/**
	 * url路径
	 */
	private String path;

	/**
	 * http请求超时时间,单位毫秒
	 */
	private int timeout;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public HttpGetType getHttpgetype() {
		return httpgetype;
	}

	public void setHttpgetype(HttpGetType httpgetype) {
		this.httpgetype = httpgetype;
	}

	public HttpType getHttptype() {
		return httptype;
	}

	public void setHttptype(HttpType httptype) {
		this.httptype = httptype;
	}

	public ContentType getContenttype() {
		return contenttype;
	}

	public void setContenttype(ContentType contenttype) {
		this.contenttype = contenttype;
	}

	@Override
	public String toString() {
		return "URLModel [ip=" + ip + ", username=" + username + ", passwd=" + passwd + ", port=" + port + ", start="
				+ start + ", size=" + size + ", httpgetype=" + httpgetype + ", httptype=" + httptype + ", contenttype="
				+ contenttype + ", packagename=" + packagename + ", scheme=" + scheme + ", host=" + host + ", path="
				+ path + ", timeout=" + timeout + "]";
	}

}
