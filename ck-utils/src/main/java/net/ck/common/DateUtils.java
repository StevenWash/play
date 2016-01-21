package net.ck.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {	
	private static DateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat time = new SimpleDateFormat("HH:mm:ss");
	private static DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取指定类型的时间格式
	 * @param dateFormat
	 * @return
	 */
	public static String getDateString(String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(new Date());
	}
	
	public static String getCurrentTimeStamp() {
		return timeStamp.format(new Date());
	}
	
	public static String getCurrentDate() {
		return date.format(new Date());
	}
	
	public static String getCurrentTime() {
		return time.format(new Date());
	}
	
	public static String getCurrentDateTime() {
		return dateTime.format(new Date());
	}
	
	public static String getTimeStamp(long value) {
		return timeStamp.format(new Date(value));
	}
	
	public static String getDate(long value) {
		return date.format(new Date(value));
	}
	
	public static String getTime(long value) {
		return time.format(new Date(value));
	}
	
	public static String getDateTime(long value) {
		return dateTime.format(new Date(value));
	}
	
	public static String getAssignDay(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, offset);
		return date.format(calendar.getTime());
	}
	
	public static void main(String[] args) {
/*		System.out.println(getCurrentDate());
		System.out.println(getCurrentTime());
		System.out.println(getCurrentDateTime());
		System.out.println(getCurrentTimeStamp());
		System.out.println("--------------------------------");
		long value = new Date().getTime();
		System.out.println(getTimeStamp(value));
		System.out.println(getTime(value));
		System.out.println(getDate(value));
		System.out.println(getDateTime(value));*/
		
		System.out.println(getAssignDay(0));
	}
}
