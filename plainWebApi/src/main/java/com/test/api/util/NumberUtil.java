package com.test.api.util;

import java.math.BigDecimal;

public class NumberUtil {
	
	/**
	 * 比较数字大小
	 * @param num1 前一个
	 * @param num2 后一个
	 * @return 前一个大返回 true，否则 false
	 */
	public static <T extends Number & Comparable<T>> boolean greaterThan(T num1, T num2) {
		if (num1 == null || num2 == null) {
			return false;
		}
		if (num1.compareTo(num2) > 0) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(NumberUtil.greaterThan(new BigDecimal("1.999999999999999999999999999999999999999991"), new BigDecimal("1.999999999999999999999999999999999999999997")));
	}

	public NumberUtil() {
	}

}
