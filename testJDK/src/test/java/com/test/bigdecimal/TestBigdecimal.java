package com.test.bigdecimal;

import java.math.BigDecimal;

import org.junit.Test;

public class TestBigdecimal {

	@Test
	public void testRound() {
		BigDecimal num1 = new BigDecimal("0.01");
		BigDecimal num2 = new BigDecimal("1.1");
		BigDecimal num3 = num1.multiply(num2).setScale(2, BigDecimal.ROUND_UP);
		System.out.println(num3.toString());
	}
}
