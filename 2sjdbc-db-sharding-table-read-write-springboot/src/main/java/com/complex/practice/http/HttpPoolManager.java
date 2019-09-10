package com.complex.practice.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  maven
 * <dependency>
	<groupId> org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.5.1</version>
</dependency>
 */

/**
 * http连接池管理
 * 
 * @author dwpeng
 *
 */
public final class HttpPoolManager {

	private static final Logger _log = LoggerFactory.getLogger(HttpPoolManager.class);

	private static HttpPoolManager poolManager = null;

	/**
	 * 连接池
	 */
	private CloseableHttpClient closeablehttpclient;

	/**
	 * 从连接池获取连接超时时间单位毫秒
	 */
	private int connectionRequestTimeout;

	/**
	 * http连接最大数
	 */
	private int maxTotal;

	/**
	 * ssl免登陆
	 */
	@SuppressWarnings("unused")
	private static boolean isssl = true;

	/**
	 * 连接池管理者
	 */
	PoolingHttpClientConnectionManager clientConnectionManager;

	private HttpPoolManager(int maxTotal, int connectionRequestTimeout) {

		this.maxTotal = maxTotal;
		this.connectionRequestTimeout = connectionRequestTimeout;
		clientConnectionManager = getPoolingHttpClientConnectionManager();
		clientConnectionManager.setMaxTotal(this.maxTotal);
		clientConnectionManager.setDefaultMaxPerRoute(this.maxTotal);
	}

	private HttpPoolManager(int maxTotal, int connectionRequestTimeout, List<HttpHostConfig> httphostconfigs) {
		this(maxTotal, connectionRequestTimeout);
		if (httphostconfigs != null) {
			for (HttpHostConfig hh : httphostconfigs) {
				HttpHost localhost = new HttpHost(hh.getHostip(), hh.getMaxperroute());
				clientConnectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);
			}
		}

	}

	/**
	 * 创建HttpPoolManager
	 * 
	 * @param maxTotal
	 *            http总的连接数
	 * @param connectionRequestTimeout
	 *            从http获取连接超时时间
	 * @return
	 */
	public synchronized static HttpPoolManager getInstance(int maxTotal, int connectionRequestTimeout) {

		if (maxTotal <= 0) {
			_log.error("maxTotal is " + maxTotal);
			return null;
		}
		if (poolManager == null) {
			poolManager = HttpPoolManager.getInstance(maxTotal, connectionRequestTimeout, null);

		}

		return poolManager;
	}

	/**
	 * 创建 HttpPoolManager
	 * 
	 * @param maxTotal
	 *            http 总的连接数
	 * @param httphostconfigs
	 *            每个主机http的最大连接数加起来不能超过总的总的连接数
	 * @return HttpPoolManager
	 */
	public synchronized static HttpPoolManager getInstance(int maxTotal, int connectionRequestTimeout,
			List<HttpHostConfig> httphostconfigs) {

		if (maxTotal <= 0) {
			_log.error("maxTotal is " + maxTotal);
			return null;
		}
		int sum = 0;
		if (httphostconfigs != null) {
			for (HttpHostConfig hh : httphostconfigs) {
				if (hh.getHostip() == null || hh.getHostip().equals("")) {

					return null;
				}
				sum += hh.getMaxperroute();

			}
		}
		if (sum > maxTotal) {
			_log.error("每个ip设置的连接数之和大于连接总数");
			return null;
		}
		if (poolManager == null) {
			poolManager = new HttpPoolManager(maxTotal, connectionRequestTimeout, httphostconfigs);
			try {
				// 创建closeablehttpclient连接对象
				MyHttpRequestRetryHandler redirectStrategy = new MyHttpRequestRetryHandler();
				// RequestConfig defaultRequestConfig =
				// RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).setExpectContinueEnabled(true)
				// .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
				// AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
				poolManager.closeablehttpclient = HttpClients.custom()
						.setConnectionManager(poolManager.clientConnectionManager).setRetryHandler(redirectStrategy)
						.build();

			} catch (Exception ex) {
				_log.error(ex.getMessage());
			}
		}
		return poolManager;
	}

	/**
	 * 获取HttpClient,首先调用getInstance方法来初始化poolManager
	 * 
	 * @return HttpClient
	 */
	public HttpClient getHttpClient() {

		// 初始化http客户端
		HttpClient httpclient = null;

		if (poolManager == null) {
			_log.error("poolManager is null");
			return httpclient;
		}
		if (closeablehttpclient == null) {

			_log.error("closeablehttpclient is null");
			return httpclient;
		}

		// 创建HttpClient
		httpclient = new HttpClient(closeablehttpclient, this.connectionRequestTimeout);
		return httpclient;
	}

	/**
	 * 返回apache 原始的连接 ,给solr提供应用
	 * 
	 * @return CloseableHttpClient
	 */
	public CloseableHttpClient getCloseableHttpClient() {

		return this.closeablehttpclient;
	}

	/**
	 * 关闭连接池
	 */
	public synchronized void close() {

		if (closeablehttpclient != null) {
			try {
				closeablehttpclient.close();
			} catch (IOException e) {
				_log.error(e.getMessage());
			}
		}

	}

	private static PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {

		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", plainsf).register("https", sslsf).build();
		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(registry);
		return clientConnectionManager;

		/*
		 * LayeredConnectionSocketFactory sslsf =
		 * SSLConnectionSocketFactory.getSocketFactory();
		 * SSLConnectionSocketFactory sf = createIgnoreVerifySSL();
		 * 
		 * ConnectionSocketFactory plainsf =
		 * PlainConnectionSocketFactory.getSocketFactory();
		 * Registry<ConnectionSocketFactory> registry = null; if (isssl) {
		 * registry = RegistryBuilder.<ConnectionSocketFactory>
		 * create().register("http", plainsf).register("https", sf).build(); }
		 * else { registry = RegistryBuilder.<ConnectionSocketFactory>
		 * create().register("http", plainsf).register("https", sslsf).build();
		 * } PoolingHttpClientConnectionManager clientConnectionManager = new
		 * PoolingHttpClientConnectionManager(registry); return
		 * clientConnectionManager;
		 */
	}

	static class MyHttpRequestRetryHandler implements HttpRequestRetryHandler {

		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if (executionCount >= 5) {// 如果已经重试了5次，就放弃
				return false;
			}
			if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
				return true;
			}
			if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
				return false;
			}
			if (exception instanceof InterruptedIOException) {// 超时
				return false;
			}
			if (exception instanceof UnknownHostException) {// 目标服务器不可达
				return false;
			}
			if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
				return false;
			}
			if (exception instanceof SSLException) {// ssl握手异常
				return false;
			}

			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			// 如果请求是幂等的，就再次尝试
			if (!(request instanceof HttpEntityEnclosingRequest)) {
				return true;
			}
			return false;
		}

	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLConnectionSocketFactory createIgnoreVerifySSL() {
		SSLContextBuilder builder = new SSLContextBuilder();
		SSLConnectionSocketFactory sslf = null;
		// 全部信任 不做身份鉴定
		try {
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			sslf = new SSLConnectionSocketFactory(builder.build(),
					new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			_log.error(e.getMessage());
		} catch (KeyStoreException e) {
			_log.error(e.getMessage());
		} catch (KeyManagementException e) {
			_log.error(e.getMessage());
		}
		return sslf;
	}

}
