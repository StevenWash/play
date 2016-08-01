package com.test.api.aop;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.api.API;
import com.test.api.APIUtil;
import com.test.api.JsonResponses;
import com.test.api.bean.BasicInfoBean;
import com.test.api.util.HttpUtil;
import com.test.api.util.StringUtil;

/**
 * 使用 aspect aop 方式统一处理 Controller 的安全，日志和异常问题
 * 
 * @author ck
 *
 */
@Aspect
public class HandleControllerAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private UserService userService;
	@Autowired
	private APIUtil apiUtil;

	@Around(value = "execution(public * com.test.api.controller.*Controller.*(..))")
	public void handleController(ProceedingJoinPoint jp) {
		long begin = System.currentTimeMillis();
		final Signature sig = jp.getSignature();
		String requestMethod = sig.getDeclaringTypeName().concat(".").concat(sig.getName());

		HttpServletResponse response = null;
		String test = "";
		String uid = "";
		String token = "";
		String salt = "";
		String user_agent = "";

		String httpMethod = "";
		String requestURI = "";
		String remoteAddr = "";
		String queryString = "";
		String pathInfo = "";
		String requestHeader = "";
		BasicInfoBean basicInfo = new BasicInfoBean();

		final Object[] objs = jp.getArgs();
		if (objs != null) {
			for (Object obj : objs) {
				if (obj instanceof HttpServletRequest) { // 获取
															// HttpServletRequest
															// 并记录请求信息
					HttpServletRequest request = (HttpServletRequest) obj;
					remoteAddr = StringUtil.getStr(apiUtil.getIpAddr(request));
					httpMethod = StringUtil.getStr(request.getMethod());
					requestURI = StringUtil.getStr(request.getRequestURI());
					pathInfo = StringUtil.getStr(request.getPathInfo());
					queryString = StringUtil.getStr(request.getQueryString());
					// 处理请求头
					for (Enumeration<?> header = request.getHeaderNames(); header.hasMoreElements();) {
						String name = StringUtil.getStr(header.nextElement());
						requestHeader += request.getHeader(name) + ";";
					}

					uid = StringUtil.getStr(request.getParameter("uid"));
					token = StringUtil.getStr(request.getParameter("token"));
					salt = StringUtil.getStr(request.getParameter("salt"));
					user_agent = StringUtil.getStr(request.getParameter("ua"));
					test = StringUtil.getStr(request.getParameter("test"));

					// 记录日志到mongodb中
					apiUtil.saveLogs(uid, remoteAddr, requestURI + "?" + queryString, 1);
				}

				if (obj instanceof HttpServletResponse) { // 获取
															// HttpServletResponse
															// 并在出现异常的时候返回给客户端错误信息
					response = (HttpServletResponse) obj;
				}

				if (obj instanceof BasicInfoBean) { // 获取 BasicInfoBean 并记录请求信息
					basicInfo = (BasicInfoBean) obj;
				}
			}
		}

		// 测试环境，当test为1的时候才不用验证
		if (API.VARIABLE.site_domain.equals("test.51offer.com") && !test.equals("1")) {
			if (!isCheckPass(uid, StringUtils.isNotBlank(token) ? token : salt, user_agent)) {
				HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeAPIFilterDefault, API.MESSAGE.AUTH_FAILED),
						response);
				return;
			}
		}

		// 验证用户是否需要能够访问
		if (API.VARIABLE.site_domain.equals("51offer.com") || API.VARIABLE.site_domain.equals("pre.51offer.com")) {
			if (!isCheckPass(uid, StringUtils.isNotBlank(token) ? token : salt, user_agent)) {
				HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeAPIFilterDefault, API.MESSAGE.AUTH_FAILED),
						response);
				return;
			}
		}

		List<String> infoList = new ArrayList<String>();
		StringBuilder infoLog = new StringBuilder();
		infoLog.append("requestMethod:{},");
		infoList.add(requestMethod);
		infoLog.append("remoteAddr:{},");
		infoList.add(remoteAddr);
		infoLog.append("httpMethod:{},");
		infoList.add(httpMethod);
		infoLog.append("requestURI:{},");
		infoList.add(requestURI);
		infoLog.append("queryString:{},");
		infoList.add(queryString);
		infoLog.append("pathInfo:{},");
		infoList.add(pathInfo);
		infoLog.append("requestHeader:{},");
		infoList.add(requestHeader);
		infoLog.append("BasicInfoBean:{},");
		infoList.add(basicInfo.toString());

		// 目标方法的处理
		try {
			jp.proceed();
		} catch (Throwable e) {
			String errorInfo = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : API.MESSAGE.METHOD_EXCEPTION;
			if (errorInfo.contains("Failfast invoke providers")
					|| errorInfo.contains("Failed to invoke remote method")) {
				int dex2 = errorInfo.indexOf("/");
				int dex3 = errorInfo.indexOf("?");
				String classname = errorInfo.substring(dex2, dex3);
				if (classname != null && classname.lastIndexOf("/") != -1) {
					classname = classname.substring(classname.lastIndexOf("/") + 1);
				}
				errorInfo = "依赖的服务出现异常," + classname;
			}
			infoLog.append("errorInfo:{}");
			infoList.add(errorInfo);
			logger.error(infoLog.toString(), infoList.toArray(new Object[infoList.size()]), e);

			HttpUtil.returnJson(new JsonResponses(API.CODE.kCodeDefault, errorInfo), response);
		} finally {
			infoLog.append("processTime:{}");
			infoList.add(String.valueOf(System.currentTimeMillis() - begin));
			logger.info(infoLog.toString(), infoList.toArray(new Object[infoList.size()]));
		}

	}

	private boolean isCheckPass(final String uid, final String token, final String user_agent) {
		if (StringUtils.isNotBlank(uid)) {
			/*
			 * Users user = userService.verifyUserLogin(0, token, user_agent,
			 * ProjectChannel.API); if (user != null &&
			 * uid.equals(StringUtil.getStr(user.getUid()))) { return true; }
			 * else { return false; }
			 */
			return true; // TODO 判断逻辑
		} else {
			return true;
		}
	}

}