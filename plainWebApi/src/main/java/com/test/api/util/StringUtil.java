package com.test.api.util;

public class StringUtil {
	
	
	/**
	 * 获取Object对象的 String对象
	 * @param obj Object对象
	 * @return
	 */
	public static boolean isNotNullAndNotBlank(Object obj) {
		return obj != null && getStr(obj).trim().length() > 0;
	}

	/**
	 * 获取Object对象的 String对象
	 * @param obj Object对象
	 * @return
	 */
	public static String getStr(Object obj) {
		return obj != null ? obj.toString() : "";
	}
	
	/**
	 * 获取Object对象的 String对象，如果为空返回默认值
	 * @param obj Object对象
	 * @param defaultStr 默认值
	 * @return
	 */
	public static String getStr(Object obj, String defaultStr) {
		String str = getStr(obj);
		return str.equals("") ? defaultStr : str;
	}
	
	/**
	 * 判断某个 Object对象 里面是否含有指定的字符串
	 * @param obj Object对象
	 * @param str 指定的字符串
	 * @return true:含有，false:不含有
	 */
	public static boolean objectHasStr(Object obj, String str) {
		String objStr = getStr(obj);
		return objStr.contains(str);
	}
}
