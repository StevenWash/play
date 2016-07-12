package com.test.api.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpUtil {
	public static void returnJson(Object object, HttpServletResponse response) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void returnHtml(Object object, HttpServletResponse response){
		try {
			response.setContentType("text/html;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 迁移自Struts2Utils
	 * @date 2015-05-06
	 * 获取ip
	 */
	public static String getRealIP(HttpServletRequest request) {
		String REALIP=request.getHeader("cdn-src-ip");
		if(StringUtils.isBlank(REALIP)){
			REALIP=request.getHeader("x-forwarded-for");
			if(StringUtils.isNotBlank(REALIP)){
				for(String IP:REALIP.split(",")){
					if(IP.startsWith("192.168")||IP.startsWith("10")||IP.startsWith("172.16")){
						continue;
					}else{
						REALIP=IP;
						break;
					}
				}
			}
			if(StringUtils.isBlank(REALIP)){
				REALIP=request.getHeader("x-real-ip");
			}
			if(StringUtils.isBlank(REALIP)){
				REALIP=request.getRemoteAddr();
			}
		}
		return REALIP;
	}
}
