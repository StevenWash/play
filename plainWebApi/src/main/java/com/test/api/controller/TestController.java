package com.test.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.api.bean.KVBean;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/test")
//@Api(value = "test", tags = "测试相关接口")
public class TestController {
	
	private Map<String, String> cacheData = Collections.synchronizedMap(new HashMap<String, String>());

	@ResponseBody
	@RequestMapping(value = "/test_cache", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "测试scope", notes = "测试scope", response = KVBean.class, responseContainer = "List")
	public List<KVBean> getTestCache(@RequestParam String key, @RequestParam String value) throws Exception {
		
		System.out.println(Thread.currentThread().getName());
		cacheData.put(key, value);
		
		List<KVBean> data = new ArrayList<KVBean>();
		for (Entry<String, String> it : cacheData.entrySet()) {
			data.add(new KVBean(it.getKey(), it.getValue()));
		}
		
		return data;
	}
	
}
