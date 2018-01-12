package com.toulezu.httpsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 通过 HttpClient 访问 https 网站例子
 * 
 * @author toulezu
 *
 */
public class HttpsClientJKS {
	private static final String KEY_STORE_TYPE_JKS = "jks";
	private static final String KEY_STORE_TYPE_P12 = "PKCS12";
	private static final String SCHEME_HTTPS = "https";
	private static final int HTTPS_PORT = 443;
	private static final String HTTPS_URL = "https://www.test.51offer.com/mobile/school/countries";
	private static final String KEY_STORE_CLIENT_PATH = "test-client.p12";
	private static final String KEY_STORE_TRUST_PATH = "test-server.jks";
	private static final String KEY_STORE_PASSWORD = "123456";
	private static final String KEY_STORE_TRUST_PASSWORD = "123456";

	public static void main(String[] args) throws Exception {
		ssl();
	}

	private static void ssl() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			// 存放客户端的私钥
			KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
			// 存放客户端收集的公钥
			KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
			InputStream ksIn = HttpsClientTrustStore.class.getClassLoader().getResourceAsStream(KEY_STORE_CLIENT_PATH);
			InputStream tsIn = HttpsClientTrustStore.class.getClassLoader().getResourceAsStream(KEY_STORE_TRUST_PATH);
			try {
				keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
				trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			}
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, KEY_STORE_PASSWORD, trustStore);
			Scheme sch = new Scheme(SCHEME_HTTPS, HTTPS_PORT, socketFactory);
			
			// 注册当使用 https 请求的时候使用的 KeyStore 和 trustStore
			
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			// 使用 http get请求
			doHttpGet(httpClient, HTTPS_URL);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private static void doHttpGet(HttpClient httpClient, String url) throws IOException, ClientProtocolException {
		HttpGet httpget = new HttpGet(url);
		System.out.println("executing request" + httpget.getRequestLine());
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: " + entity.getContentLength());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				System.out.println(text);
			}
			bufferedReader.close();
		}
		EntityUtils.consume(entity);
	}
}
