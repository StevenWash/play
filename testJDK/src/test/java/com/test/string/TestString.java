package com.test.string;

import org.junit.Test;

public class TestString {

	@Test
	public void testStringFormat() {
		/**
		 * %s ��ʽ���ַ�����
		 * %-10s �������ҿ�����ʾ10���ַ�����������Կո񲹳�
		 * %10s�����ҵ��������ʾ10���ַ�����������Կո񲹳�
		 * 
		 * 
		 */
		String str = "sdfdf";
		String forstr = String.format("%10s", str);
		System.out.println(forstr);
	}
}
