package com.test.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class APIUtil {
	public static Logger logger = Logger.getLogger(APIUtil.class);

	//@Autowired
	//private APIMemcachedManager memcachedManager;

	//@Autowired
	//public LogService logService;

	/**
	 * 根据uid获取请求标识
	 * 
	 * @param uid
	 * @return
	 */
	public String getSalt(String uid) {/*
		String key = "";
		try {
			key = (String) memcachedManager.get("userLoginVerify_" + uid);
		} catch (Exception e) {
			logger.error("query memcached salt error:{}", e);
		}
		if (key == null)
			key = "";
		return key;
	*/
	return null;
	// TODO memcached 相关
	}

	/**
	 * 记录访问流水账
	 * 
	 * @param uid
	 *            用户id
	 * @param url
	 *            请求地址
	 * @param type
	 *            请求类型:1为合法,2为非法
	 */
	public void saveLogs(String uid, String ip, String url, int type) {
		try {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("uid", uid);
			dbObject.put("url", url);
			dbObject.put("type", type);
			dbObject.put("ip", ip);
			dbObject.put("created", new Date());
			//logService.saveLog(dbObject);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 获取请求参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getParam(HttpServletRequest request) {
		// ipAddr = getIpAddr(request);
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		try {
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				String paramValue = paramValues[0];
				// if (paramValue.length() != 0) {
				// System.out.println("参数：" + paramName + "=" + paramValue);
				// 选填项如果没值默认就不插入 modify by arvin.lu
				if ((null != paramValue) && (!"".equals(paramValue))) {
					sb.append(paramName + "=" + paramValue + "&");
				}
			}
			if (sb.toString().length() == 0)
				return "";
			else
				return sb.toString().substring(0, sb.toString().length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取app版本
	 * 
	 * @param ua
	 * @param version
	 * @return
	 */
	public Map<String, Object> appVersion(String ua, String version) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 加载配置文件
			Properties config = new Properties();
			InputStream input = null;
			try {
				// Modified by Tangm at 20150507 删掉config/
				if (ua.substring(0, 1).equals("a")) {
					input = APIUtil.class.getClassLoader().getResourceAsStream("app-android.properties");
				} else if (ua.substring(0, 1).equals("i")) {
					input = APIUtil.class.getClassLoader().getResourceAsStream("app-ios.properties");
				}
				Reader reader = new InputStreamReader(input, "UTF-8");
				config.load(reader);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != input) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
			map.put("user", config.getProperty("user_" + version));
			map.put("apply", config.getProperty("apply_" + version));
			map.put("mail", config.getProperty("mail_" + version));
			map.put("push", config.getProperty("push_" + version));
			map.put("appver", config.getProperty("appver_" + version));
			map.put("do", config.getProperty("do_" + version));
			map.put("pay", config.getProperty("pay_" + version));
			map.put("school", config.getProperty("school_" + version));
			map.put("dashboard", config.getProperty("dashboard_" + version));
			map.put("sys", config.getProperty("sys_" + version));
			map.put("dict", config.getProperty("dict_" + version));
			map.put("studying", config.getProperty("studying_" + version));
			map.put("userStudyingTemp", config.getProperty("userStudyingTemp_" + version));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 检查手机是否是正常发短信(未登陆)
	 * 
	 * @param region
	 * @param mobile
	 * @return
	 */
	public boolean checkCustomer(String customerId) {/*
		try {
			// 检查该游客发送验证码次数
			int count = 0;
			Object o = memcachedManager.get("count_" + customerId);
			if (o != null) {
				count = Integer.parseInt(String.valueOf(o));
				// 次数未超过限制
				if (count < message_limit) {
					// 次数加1
					memcachedManager.add("count_" + customerId, 60 * 60, ++count);
					return true;
				}
				return false;
			}
			// memcached里没存则说明该游客还未发过验证码短信
			else {
				memcachedManager.add("count_" + customerId, 60 * 60, 1);
				return true;
			}
			// 否则往memcached里存该游客
		} catch (Exception e) {
			logger.error("check failure:{}", e);
			return false;
		}
	*/
		// TODO memcached 相关
		return true;
	}

	// 每个游客每天能收到的验证码信息条数
	// private static final int message_limit = 5;

	/**
	 * 获取IP
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
