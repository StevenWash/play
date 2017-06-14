package com.test.api.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.api.API;
import com.test.api.JsonResponses;
import com.test.api.bean.BasicInfoBean;
import com.test.api.util.HttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户 登录/注册
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
@Api(value = "user", tags = "用户相关接口")
public class UserController {

	// 学校广场
	//@Resource
	//private UserService userServicebyUC;
	
	@ResponseBody
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", response = JsonResponses.class)
	@RequestMapping(value = "/user_info", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public void getUserInfo(@ModelAttribute BasicInfoBean basicInfo, 
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		// 验证uid是否存在
		Integer uid = basicInfo.getUid();
		if (uid == null) {
			HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeParamsError, API.MESSAGE.PARAM_FAILED.concat(":uid为空")), response);
			return;
		}
		
		//Users user = userServicebyUC.getUsersByUid(uid);
		final Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("uid", uid);
		//setUserModelMap(user, dataMap, basicInfo);
		
		HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeSuccess, API.MESSAGE.SUCCESS, dataMap), response);
	}
	

}
