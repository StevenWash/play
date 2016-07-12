package com.test.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

/**
 * 静态资源
 * 
 * @author ck
 *
 */
public class API {

	/**
	 * API 辅助工具类
	 * 
	 * @author Administrator
	 *
	 */
	public static class METHOD {
		
		/**
		 * 比较版本号的大小,
		 * @param version1
		 * @param version2
		 * @return 前者大返回一个正数, 否则返回一个负数（任意一个版本为空返回-1），相等返回0
		 */
		public static int compareVersion(String version1, String version2) throws Exception {
			if (StringUtils.isBlank(version1) || StringUtils.isBlank(version2)) {
				return -1;
			}
			String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
			String[] versionArray2 = version2.split("\\.");
			int idx = 0;
			int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
			int diff = 0;
			while (idx < minLength
					&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
					&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
				++idx;
			}
			//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
			diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
			return diff;
		}

		/**
		 * 获取异常信息
		 * 
		 * @param e
		 * @return String
		 */
		public static String getMsg(Throwable e) {
			if (e.getMessage() != null) {
				return e.getMessage();
			}
			return e.toString();
		}

		public static String getISOTime(final Object date) throws Exception {
			if (date == null) {
				return null;
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dateTime = sdf.parse(date.toString());
				return new DateTime(dateTime.getTime()).toString();
			} catch (Exception e) {
				throw new IllegalArgumentException("日期格式有误");
			}
		}
		
		public static String getISOTimeByDate(Date date) throws Exception {
			if (date == null) {
				return null;
			}
			return new DateTime(date.getTime()).toString();
		}

		/**
		 * 判断字符串是否可以转成合法的日期
		 * @param dateStr
		 * @return
		 */
		public static boolean isValidDate(String dateStr) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setLenient(false);
				sdf.parse(dateStr);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		public static Date getDate(String dateStr) throws Exception {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		}
		
		public static String getDateStr(Date date) throws Exception {
			if (date == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}

	}

	/**
	 * 常量
	 * 
	 * @author Administrator
	 *
	 */
	public static class CONSTANT {

		public static final String GET = "GET";
		public static final String POST = "POST";
		public static final String PUT = "PUT";
		public static final String DELETE = "DELETE";
		
	}

	/**
	 * 变量
	 * 
	 * @author Administrator
	 *
	 */
	public static class VARIABLE {

		public static String site_domain;
		
		static {
			// 加载配置文件
			Properties config = new Properties();
			InputStream input = null;
			try {
				input = API.class.getClassLoader().getResourceAsStream("api.properties");
				Reader reader = new InputStreamReader(input, "UTF-8");
				config.load(reader);
				
				site_domain = config.getProperty("site_domain");
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
		}
	}

	/**
	 * 具体消息说明
	 * 
	 * @author ck
	 *
	 */
	public static class MESSAGE {
		public static final String AUTH_FAILED = "身份验证失败,请重新登录";
		public static final String METHOD_EXCEPTION = "出现异常";
		public static final String PARAM_FAILED = "参数错误";
		public static final String SUCCESS = "成功";
		public static final String FAILED = "失败";
		
	}

	/**
	 * 公司通用 code
	 * 
	 * @author ck
	 *
	 */
	public static class CODE {
		public static final int kCodeDefault = 40000;
		public static final int kCodeAPIFilterDefault = 40001;
		public static final int kCodeParamsError = 40002;
		public static final int kCodeSuccess = 200;
	}

}
