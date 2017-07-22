package com.test.api.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.api.API;
import com.test.api.JsonResponses;
import com.test.api.bean.BasicInfoBean;
import com.test.api.bean.User;
import com.test.api.util.HttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户相关
 * @author ckjava
 *
 */
@Controller
@RequestMapping("/user")
@Api(value = "user", tags = "用户相关接口")
public class UserController {
	
	private static AtomicInteger uid = new AtomicInteger();
	private static Map<Integer, User> userTemp = Collections.synchronizedMap(new HashMap<Integer, User>());
	
	static {
		userTemp.put(uid.incrementAndGet(), new User("1", "陈爱歌", "男", 25, "18616866661", "18616866661@qq.com"));
		userTemp.put(uid.incrementAndGet(), new User("2", "段久剑", "男", 28, "18616866662", "18616866662@qq.com"));
		userTemp.put(uid.incrementAndGet(), new User("3", "李春宏", "女", 25, "18616866663", "18616866663@qq.com"));
		userTemp.put(uid.incrementAndGet(), new User("4", "赵仙幽", "男", 23, "18616866664", "18616866664@qq.com"));
		userTemp.put(uid.incrementAndGet(), new User("5", "刘梦里", "女", 25, "18616866665", "18616866665@qq.com"));
	}

	@ResponseBody
	@ApiOperation(value = "获取所有用户信息", response = JsonResponses.class, responseContainer = "Map")
	@RequestMapping(value = "/user ", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public void getUserList(@ModelAttribute BasicInfoBean basicInfo, 
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, userTemp), response);
	}
	
	@ResponseBody
	@ApiOperation(value = "获取单个用户信息", response = JsonResponses.class)
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public void getUser(@ModelAttribute BasicInfoBean basicInfo, @PathVariable Integer id,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, userTemp.get(id)), response);
	}
	
	@ResponseBody
	@ApiOperation(value = "删除单个用户信息", response = JsonResponses.class)
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public void deleteUser(@ModelAttribute BasicInfoBean basicInfo, @PathVariable Integer id,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, userTemp.remove(id)), response);
	}
	
	@ResponseBody
	@ApiOperation(value = "新增用户信息", response = JsonResponses.class)
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void addUser(@ModelAttribute BasicInfoBean basicInfo, @RequestBody User user,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		Integer newid = uid.incrementAndGet();
		userTemp.put(newid, user);
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, userTemp.get(newid)), response);
	}
	
	@ResponseBody
	@ApiOperation(value = "修改用户信息", response = JsonResponses.class)
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public void updateUser(@ModelAttribute BasicInfoBean basicInfo, @PathVariable Integer id, @RequestBody User user,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		userTemp.put(id, user);
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, userTemp.get(id)), response);
	}

}
