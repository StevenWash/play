package com.test.string;

import org.junit.Test;

public class TestString {

	@Test
	public void testStringFormat() {
		/**
		 * %s 格式化字符串，
		 * %-10s ：从左到右可以显示10个字符，如果不够以空格补充
		 * %10s：从右到左可以显示10个字符，如果不够以空格补充
		 * 
		 * 
		 */
		String str = "sdfdf";
		String forstr = String.format("%10s", str);
		System.out.println(forstr);
	}
}
