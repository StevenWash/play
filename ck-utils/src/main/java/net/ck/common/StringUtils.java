package net.ck.common;

public class StringUtils {
	
	public static String getUTF8Str(String ocharset, String str) throws Exception {
		try {
			if (str != null && ocharset != null) {
				return new String(str.getBytes(ocharset), "UTF-8");
			}
			return null;
		} catch (Exception e) {
			throw new Exception("get string UTF-8 string occurrence error", e);
		}
	}
	
	public static String getProperStr(String ocharset, String str) throws Exception {
		try {
			if (str != null && ocharset != null) {
				return new String(str.getBytes(ocharset), ocharset);
			}
			return null;
		} catch (Exception e) {
			throw new Exception("get proper string occurrence error", e);
		}
	}
	
	public static String getStr(Object object) {
		return object != null ? object.toString() : "";
	}
	
	public static String getStr(Object object, String defaultString) {
		return object != null ? (object.toString().equals("") ? defaultString : object.toString()) : defaultString;
	}
	
	public static String getCleanStr(Object object) {
		return object != null ? object.toString().replace("'", "‘").replace("\"", "”") : "";
	}
	
	public static boolean isNotEmpty(String str) {
		if (str != null && str.length() > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}
	
}
