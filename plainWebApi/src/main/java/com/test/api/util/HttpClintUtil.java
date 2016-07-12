package com.test.api.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpClintUtil {
	private static Logger log = LoggerFactory.getLogger(HttpClintUtil.class);
	private static final Map<String, String> commonPara = new HashMap<String, String>();
	
	static {
		commonPara.put("appver", "2.9");
		commonPara.put("uid", "5");
		commonPara.put("ua", "i_9.0_750_1334_225F3886-430E-4106-9A8D-7B386F174209_iPhone-Simulator");
		commonPara.put("src", "231000");
		commonPara.put("uuid", "3C075CCE-0B6D-40F6-911C-785A6CEF15E8");
		commonPara.put("apiversion", "5");
		commonPara.put("salt", "46308d55-dde0-40ee-bfb6-0bfb6b0d46d3");
		commonPara.put("test", "1");
	}
	
	public static String put(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String body = null;

		log.info("create httpput:" + url);
		HttpPut put = putForm(url, params);
		
		body = invoke(httpclient, put);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		
		boolean flag = true;
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			if (flag) {
				url += "?";
				flag = false;
			}
			url += "&" + entry.getKey() + "=" + entry.getValue();
		}

		log.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httprequest) {
		HttpResponse response = sendRequest(httpclient, httprequest);
		return paseResponse(response);
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			log.error("ParseException...");
		} catch (IOException e) {
			log.error("IOException...");
		}
		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest request) {
		log.info("execute request...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			log.error("ClientProtocolException...");
		} catch (IOException e) {
			log.error("IOException...");
		}
		
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");

		try {
			String jsonstr = JSONObject.toJSONString(params);
			
			StringEntity se = new StringEntity(jsonstr, "UTF-8");
		    se.setContentType("application/json");
		    se.setContentEncoding("UTF-8");
		    
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(se);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}
	
	private static HttpPut putForm(String url, Map<String, String> params) {
		HttpPut httput = new HttpPut(url);
		httput.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
		
		try {
			String jsonstr = JSONObject.toJSONString(params);
			
			StringEntity se = new StringEntity(jsonstr, "UTF-8");
		    se.setContentType("application/json");
		    se.setContentEncoding("UTF-8");
		    
		    log.info("set utf-8 form entity to httput");
		    httput.setEntity(se);
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException...");
		}

		return httput;
	}
	
	public static void main(String[] args) {}
}
