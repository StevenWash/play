package net.androidla.httpclient;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpClientHelper {

	private static Logger LOG = LoggerFactory.getLogger(HttpClientHelper.class);

	private HttpClient httpclient = new DefaultHttpClient();
	private HttpContext localContext = new BasicHttpContext();
	private BasicCookieStore basicCookieStore = new BasicCookieStore();

	private int TIME_OUT = 3;

	public HttpClientHelper() {
		instance();
	}

	/**
	 * 启用cookie存储
	 */
	private void instance() {
		//httpclient = getProxyHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", TIME_OUT * 1000);
		localContext.setAttribute("http.cookie-store", basicCookieStore);
	}

	/**
	 * @param ssl
	 *            boolean=true 支持https网址，false同默认构造
	 */
	public HttpClientHelper(boolean ssl) {
		instance();
		if (ssl) {
			try {
				X509TrustManager tm = new X509TrustManager() {

					public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				};
				SSLContext ctx = SSLContext.getInstance("TLS");
				ctx.init(null, new TrustManager[] { tm }, null);
				SchemeLayeredSocketFactory ssf = new SSLSocketFactory(ctx);
				ClientConnectionManager ccm = httpclient.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", 443, ssf));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param url
	 * @param headers
	 *            指定headers
	 * @return
	 */
	public HttpResult get(String url, Header... headers) throws Exception {
		HttpResponse response;
		HttpGet httpget = new HttpGet(url);
		if (headers != null) {
			for (Header h : headers) {
				httpget.addHeader(h);
			}
		} else {// 如不指定则使用默认
			Header header = new BasicHeader(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1;  .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2)");
			httpget.addHeader(header);
		}
		HttpResult httpResult = HttpResult.empty();
		try {
			response = httpclient.execute(httpget, localContext);
			httpResult = new HttpResult(localContext, response);
		} catch (IOException e) {
			LOG.error(" get 出现异常");
			httpget.abort();
			throw e;
		}
		return httpResult;
	}
	
	public HttpResult post(String url, Map<String, String> data, Header... headers) throws Exception {
		HttpResponse response;
		HttpPost httppost = new HttpPost(url);
		String contentType = null;
		if (headers != null) {
			int size = headers.length;
			for (int i = 0; i < size; ++i) {
				Header h = (Header) headers[i];
				if (!(h.getName().startsWith("$x-param"))) {
					httppost.addHeader(h);
				}
				if ("Content-Type".equalsIgnoreCase(h.getName())) {
					contentType = h.getValue();
				}
			}

		}
		if (contentType != null) {
			httppost.setHeader("Content-Type", contentType);
		} else if (data != null) {
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (String key : data.keySet()) {
			formParams.add(new BasicNameValuePair(key, (String) data.get(key)));
		}
		HttpResult httpResult = HttpResult.empty();
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
			httppost.setEntity(entity);
			response = httpclient.execute(httppost, localContext);
			httpResult = new HttpResult(localContext, response);
		} catch (Exception e) {
			LOG.error(" post 出现异常");
			try {
				httppost.abort();
			} catch (Exception e2) {
				throw e2;
			}
			throw e;
		}
		return httpResult;
	}

	/**
	 * 获取指定的Cookie
	 * @param name
	 * @param domain
	 * @return
	 */
	public String getCookie(String name, String... domain) {
		String dm = "";
		if (domain != null && domain.length >= 1) {
			dm = domain[0];
		}
		for (Cookie c : basicCookieStore.getCookies()) {
			if (StringUtils.equals(name, c.getName()) && StringUtils.equals(dm, c.getDomain())) {
				return c.getValue();
			}
		}
		return null;
	}

	/**
	 * 打印所有的Cookie
	 */
	public void printCookieAll() {
		for (Cookie c : basicCookieStore.getCookies()) {
			System.out.println(c);
		}
	}
	
	/**
	 * 格式化HTML文本
	 * @param content
	 * @return
	 */
	public static String html(String content) {
		if(content==null) return "";
		String html = content;
		//html = StringUtils.replace(html, "'", "&apos;");
		//html = StringUtils.replace(html, "\"", "&quot;");
		//html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// �滻���
		//html = StringUtils.replace(html, " ", "&nbsp;");// �滻�ո�
		html = StringUtils.replace(html, "<", "&lt;");
		html = StringUtils.replace(html, ">", "&gt;");
		html = StringUtils.replace(html, "&", "&amp;");
		return html;
	}
	
	/**
	 * 获取有代理的 HttpClient
	 * @return
	 */
	public HttpClient getProxyHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String proxyHost = "10.195.113.100";
		int proxyPort = 8002;
		String userName = "fushiguang";
		String password = "Wisleyok1234";
		
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(userName, password));
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		return httpClient;
	}
	
}
