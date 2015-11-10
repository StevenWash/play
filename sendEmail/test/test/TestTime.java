package test;

import java.util.Date;

import net.androidla.util.DateUtils;

public class TestTime {
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date().getTime());
		System.out.println(DateUtils.getCurrentDateTime());
		long s = System.currentTimeMillis() + 1000 * 60 * 60;
			
		System.out.println(DateUtils.getDateTime(s));
	}
}
