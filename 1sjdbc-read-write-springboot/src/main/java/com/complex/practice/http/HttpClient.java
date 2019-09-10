package com.complex.practice.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.complex.practice.http.model.HttpResult;
import com.complex.practice.http.model.URLModel;
import com.complex.practice.http.type.ContentType;


/**
 * 主要负责http请求
 * 
 * @author dwpeng
 * @param <T>
 */
public final class HttpClient {

	private static Logger log = LoggerFactory.getLogger(HttpClient.class);

	/**
	 * http 连接池
	 */
	private CloseableHttpClient httpclient;

	/**
	 * 从连接池获取连接超时时间,单位毫秒
	 */
	private int connectionRequestTimeout;

	/**
	 * 默认请求超时时间
	 */
	private int reqTimeout = 2000;

	public HttpClient() {

	}

	protected HttpClient(CloseableHttpClient httpclient, int connectionRequestTimeout) {

		this.httpclient = httpclient;
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	/**
	 * http get方法
	 * 
	 * @param urlmodel
	 *            url对象
	 * @param parameter
	 *            get参数
	 * @return HttpResult
	 * @throws Exception
	 */
	public synchronized HttpResult get(URLModel urlmodel, Map<String, String> parameter) {

		log.debug("urlmodel:" + urlmodel);
		HttpResult hr = null;
		if (urlmodel == null) {
			log.error("urlmodel is null");
			return hr;
		}
		if (urlmodel.getScheme() == null) {
			log.error("scheme is null");
			return hr;
		}
		if (urlmodel.getHost() == null || urlmodel.getHost().equals("")) {
			log.error("host is null");
			return hr;
		}
		if (urlmodel.getPort() == 0) {
			log.error("port is null");
			return hr;
		}

		if (urlmodel.getTimeout() == 0) {
			log.error("timeout is null");
			return hr;
		}
		hr = get(urlmodel.getScheme(), urlmodel.getHost(), urlmodel.getPort(), urlmodel.getPath(), null, urlmodel.getTimeout(), parameter, urlmodel.getUsername(), urlmodel.getPasswd());
		return hr;
	}

	/**
	 * @param url
	 *            url地址不包含get请求
	 * @param timeout
	 *            http请求超时设置,单位毫秒
	 * @param parameter
	 *            get请求参数
	 * @return HttpResult
	 */
	public synchronized HttpResult get(String url, int timeout, Map<String, String> parameter) {

		log.debug("get url:" + url);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}
		if (timeout == 0) {
			log.error("timeout is 0");
			return hr;
		}

		hr = get(url, timeout, parameter, null, null);
		return hr;
	}

	/**
	 * @param url
	 *            完整路径
	 * @param timeout
	 *            http请求超时设置
	 * @return HttpResult
	 */
	public synchronized HttpResult get(String url, int timeout) {

		log.debug("get url:" + url);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}
		if (timeout == 0) {
			log.error("timeout is 0");
			return hr;
		}

		hr = get(url, timeout, null, null);
		return hr;
	}

	/**
	 * @param url
	 *            完整路径
	 * @return HttpResult
	 */
	public synchronized HttpResult get(String url) {

		log.debug("get url:" + url);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}

		hr = get(url, reqTimeout, null, null);
		return hr;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            url类型
	 * @param contenttype
	 *            ContentType josn/xml
	 * @param timeout
	 *            http请求超时时间
	 * @param str
	 *            post 请求字符串
	 * @return HttpResult
	 */
	public synchronized HttpResult post(String url, ContentType contenttype, int timeout, String str) {
		log.debug("post urlmodel:" + url + " str:" + str);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}
		if (contenttype == null) {
			log.error("contenttype is null");
			return hr;
		}
		if (timeout == 0) {
			log.error("timeout is 0");
			return hr;
		}

		hr = post(url, contenttype.getContentType(), timeout, null, null, str);

		return hr;
	}
	
	public synchronized HttpResult post(String url, ContentType contenttype, int timeout, String str,String charType) {
		log.debug("post urlmodel:" + url + " str:" + str);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}
		if (contenttype == null) {
			log.error("contenttype is null");
			return hr;
		}
		if (timeout == 0) {
			log.error("timeout is 0");
			return hr;
		}

		hr = post(url, contenttype.getContentType(), timeout, null, null, str,charType);

		return hr;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            url类型
	 * @param str
	 *            post 请求字符串
	 * @return HttpResult
	 */
	public synchronized HttpResult post(String url, String str) {
		log.debug("post urlmodel:" + url + " str:" + str);
		return post(url, ContentType.JSON, reqTimeout, str);
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            url类型
	 * @param str
	 *            post 请求字符串 ,json
	 * 
	 * @param reqTimeout
	 *            ,请求超时设置
	 * @return HttpResult
	 */
	public synchronized HttpResult post(String url, String str, int reqTimeout) {
		log.debug("post urlmodel:" + url + " str:" + str);
		return post(url, ContentType.JSON, reqTimeout, str);
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 *            url类型
	 * @param contenttype
	 *            ContentType josn/xml
	 * @param timeout
	 *            http请求超时时间
	 * @param str
	 *            post 请求字符串
	 * @return HttpResult
	 */
	public synchronized HttpResult post(String url, ContentType contenttype, String authorization, int timeout, String str) {
		log.debug("post urlmodel:" + url + " str:" + str);
		HttpResult hr = null;
		if (url == null || url.trim().equals("")) {
			log.error("urlmodel is null");
			return hr;
		}
		if (contenttype == null) {
			log.error("contenttype is null");
			return hr;
		}
		if (authorization == null) {
			log.error("authorization is null");
			return hr;
		}
		if (timeout == 0) {
			log.error("timeout is 0");
			return hr;
		}

		hr = post(url, contenttype.getContentType(), authorization, timeout, null, null, str);

		return hr;
	}

	/**
	 * http post 请求
	 * 
	 * @param urlmodel
	 *            url对象
	 * @param str
	 *            要发送的字符串json/xml
	 * @return HttpResult
	 */
	public synchronized HttpResult post(URLModel urlmodel, String str) {
		log.debug("post urlmodel:" + urlmodel + " str:" + str);
		HttpResult hr = null;
		if (urlmodel == null) {
			log.error("urlmodel is null");
			return hr;
		}
		if (urlmodel.getScheme() == null) {
			log.error("scheme is null");
			return hr;
		}
		if (urlmodel.getHost() == null || urlmodel.getHost().equals("")) {
			log.error("host is null");
			return hr;
		}
		if (urlmodel.getPort() == 0) {
			log.error("port is null");
			return hr;
		}
		if (urlmodel.getContenttype() == null) {
			log.error("contenttype is null");
			return hr;
		}
		if (urlmodel.getTimeout() == 0) {
			log.error("timeout is null");
			return hr;
		}

		hr = post(urlmodel.getScheme(), urlmodel.getHost(), urlmodel.getPort(), urlmodel.getPath(), urlmodel.getContenttype().getContentType(), urlmodel.getTimeout(), urlmodel.getUsername(),
				urlmodel.getPasswd(), str);

		return hr;
	}

	private HttpResult get(String scheme, String host, int port, String path, String contenttype, int timeout, Map<String, String> parameters, String usernname, String password) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// URI
		URI uri = null;
		// http get请求
		HttpGet httpget = null;
		URIBuilder uribuilder = new URIBuilder();
		// 设置协议
		uribuilder.setScheme(scheme);
		// 设置主机
		uribuilder.setHost(host);
		// 设置端口
		uribuilder.setPort(port);
		// 设置路径
		uribuilder.setPath(path);
		// 设置get参数
		if (parameters != null) {

			Set<Entry<String, String>> entrySet = parameters.entrySet();
			// 设置get参数
			for (Entry<String, String> entry : entrySet) {

				uribuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}
		try {
			uri = uribuilder.build();
		} catch (URISyntaxException ex) {
			log.error("url异常：" + uribuilder.toString());
			log.error("url异常:", ex);
			return httpresult;

		}

		log.debug("get url:" + uri.toString());
		httpget = new HttpGet(uri);
		httpget.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());

		try {

			response = httpclient.execute(httpget, HttpClientContext.create());
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			log.error("get 请求异常：" + uri.toString());
			log.error("get 请求异常", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

	/**
	 * @param url
	 *            http地址不包含get参数
	 * @param timeout
	 *            http请求超时时间
	 * @param parameters
	 *            get请求参数
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	private HttpResult get(String url, int timeout, Map<String, String> parameters, String usernname, String password) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// http get请求
		HttpGet httpget = null;
		// get 参数
		String sendstr = null;
		// 初始化get请求
		httpget = new HttpGet(url);
		// 设置get参数
		if (parameters != null) {
			List<NameValuePair> getPara = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = parameters.entrySet();
			for (Entry<String, String> entry : entrySet) {
				BasicNameValuePair np = new BasicNameValuePair(entry.getKey(), entry.getValue());
				getPara.add(np);
			}
			try {
				sendstr = EntityUtils.toString(new UrlEncodedFormEntity(getPara));
				httpget.setURI(new URI(httpget.getURI().toString() + "?" + sendstr));
			} catch (Exception ex) {
				log.error("转换get参数错误");
			}
		}
		httpget.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());

		try {

			response = httpclient.execute(httpget, HttpClientContext.create());
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			log.error("get 请求异常：" + url);
			log.error("get 请求异常：", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

	/**
	 * @param url
	 *            http地址包含get参数,例如http://www.baidu.com/
	 * @param timeout
	 *            http请求超时时间
	 * @param parameters
	 *            get请求参数
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	private HttpResult get(String url, int timeout, String usernname, String password) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// http get请求
		// 初始化get请求
		HttpGet httpget = new HttpGet(url);

		httpget.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());

		try {

			response = httpclient.execute(httpget, HttpClientContext.create());
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			log.error("get 请求异常：" + url);
			log.error("get 请求异常：", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

	private HttpResult post(String scheme, String host, int port, String path, String contenttype, int timeout, String usernname, String password, String json) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// URI
		URI uri = null;
		// http get请求
		HttpPost httppost = null;
		URIBuilder uribuilder = new URIBuilder();
		// 设置协议
		uribuilder.setScheme(scheme);
		// 设置主机
		uribuilder.setHost(host);
		// 设置端口
		uribuilder.setPort(port);
		// 设置路径
		uribuilder.setPath(path);
		try {
			uri = uribuilder.build();
		} catch (URISyntaxException ex) {
			log.error("url异常：" + uribuilder.toString());
			log.error("url异常", ex);
			return httpresult;
		}
		log.debug("get url:" + uri.toString());
		httppost = new HttpPost(uri);
		httppost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());
		httppost.setHeader("Content-Type", contenttype);
		StringEntity s = new StringEntity(json, "UTF-8");
		httppost.setEntity(s);
		try {
			response = httpclient.execute(httppost, HttpClientContext.create());
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			log.error("post 请求异常：" + uri.toString());
			log.error("post 请求异常", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

	/**
	 * @param url
	 *            地址
	 * @param contenttype
	 *            请求类型
	 * @param timeout
	 *            设置超时时间
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @param json
	 *            传入json字符串
	 * @return HttpResult
	 */
	private HttpResult post(String url, String contenttype, int timeout, String usernname, String password, String json) {
		return post( url,  contenttype,  timeout,  usernname, password,  json, "UTF-8");
	}
	
	/**
	 * @param url
	 *            地址
	 * @param contenttype
	 *            请求类型
	 * @param timeout
	 *            设置超时时间
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @param json
	 *            传入json字符串
	 * @return HttpResult
	 */
	private HttpResult post(String url, String contenttype, int timeout, String usernname, String password, String json,String charType) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// http get请求
		HttpPost httppost = new HttpPost();
		log.debug("post url:" + url);
		httppost = new HttpPost(url);
		httppost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());
		httppost.setHeader("Content-Type", contenttype);
		StringEntity s = new StringEntity(json, charType);
		httppost.setEntity(s);
		try {
			response = httpclient.execute(httppost, HttpClientContext.create());
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity,charType));
			}
		} catch (IOException e) {
			log.error("post 请求异常：" + url);
			log.error("post 请求异常：", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

	/**
	 * Basic验证的http请求
	 * 
	 * @param url
	 *            地址
	 * @param contenttype
	 * 
	 * @param authorization
	 *            Authorization 请求类型
	 * @param timeout
	 *            设置超时时间
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @param json
	 *            传入json字符串
	 * @return HttpResult
	 */
	private HttpResult post(String url, String contenttype, String authorization, int timeout, String usernname, String password, String json) {

		
		return post( url,  contenttype,  authorization,  timeout,  usernname,  password,  json,"UTF-8");
	}
	
	
	/**
	 * Basic验证的http请求
	 * 
	 * @param url
	 *            地址
	 * @param contenttype
	 * 
	 * @param authorization
	 *            Authorization 请求类型
	 * @param timeout
	 *            设置超时时间
	 * @param usernname
	 *            用户名
	 * @param password
	 *            密码
	 * @param json
	 *            传入json字符串
	 * @return HttpResult
	 */
	private HttpResult post(String url, String contenttype, String authorization, int timeout, String usernname, String password, String json,String charType) {

		// http 返回结构
		HttpResult httpresult = null;
		// response对象
		CloseableHttpResponse response = null;
		// http get请求
		HttpPost httppost = new HttpPost();
		log.debug("post url:" + url);
		httppost = new HttpPost(url);

		httppost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(connectionRequestTimeout).build());
		httppost.setHeader("Content-Type", contenttype);
		httppost.setHeader("Authorization", "Basic " + authorization);
		StringEntity s = new StringEntity(json, charType);
		httppost.setEntity(s);
		try {
			// CredentialsProvider credsProvider = new
			// BasicCredentialsProvider();
			// credsProvider.setCredentials(new
			// AuthScope(httppost.getURI().getHost(),
			// httppost.getURI().getPort()),
			// new UsernamePasswordCredentials("6b614d222bc71e30df611dd4",
			// "ca5b7762a57519359f013395"));
			// AuthCache authCache = new BasicAuthCache();
			// 把AutoCache添加到上下文中
			HttpClientContext context = HttpClientContext.create();
			// context.setCredentialsProvider(credsProvider);
			// context.setAuthCache(authCache);
			response = httpclient.execute(httppost, context);
			httpresult = new HttpResult();
			int status = response.getStatusLine().getStatusCode();
			httpresult.setStatus(status);
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				httpresult.setHttpresult(EntityUtils.toString(entity));
			} else {
				HttpEntity entity = response.getEntity();
				log.error("请求失败" + EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			log.error("post 请求异常：" + url);
			log.error("post 请求异常", e);
			return httpresult;

		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {

			}
		}
		return httpresult;
	}

}
