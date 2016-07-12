package com.test.api.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class JsonTools {
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(final String data) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			if (StringUtils.isBlank(data)) {
				return result;
			}
			return JSON.toJavaObject(JSON.parseObject(data), Map.class);
		} catch (Exception e) {
			return null;
		}
	}
}
