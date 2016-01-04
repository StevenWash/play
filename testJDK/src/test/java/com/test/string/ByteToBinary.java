package com.test.string;

public class ByteToBinary {
	/**
	 * 把byte数组转化成2进制字符串
	 * 
	 * @param bArr
	 * @return
	 */
	public static String getBinaryStrFromByteArr(byte[] bArr) {
		String result = "";
		for (byte b : bArr) {
			result += getBinaryStrFromByte(b);
		}
		return result;
	}

	/**
	 * 把byte转化成2进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getBinaryStrFromByte(byte b) {
		String result = "";
		byte a = b;
		;
		for (int i = 0; i < 8; i++) {
			byte c = a;
			a = (byte) (a >> 1);// 每移一位如同将10进制数除以2并去掉余数。
			a = (byte) (a << 1);
			if (a == c) {
				result = "0" + result;
			} else {
				result = "1" + result;
			}
			a = (byte) (a >> 1);
		}
		return result;
	}

	/**
	 * 把byte转化成2进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getBinaryStrFromByte2(byte b) {
		String result = "";
		byte a = b;
		;
		for (int i = 0; i < 8; i++) {
			result = (a % 2) + result;
			a = (byte) (a >> 1);
		}
		return result;
	}

	/**
	 * 把byte转化成2进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getBinaryStrFromByte3(byte b) {
		String result = "";
		byte a = b;
		;
		for (int i = 0; i < 8; i++) {
			result = (a % 2) + result;
			a = (byte) (a / 2);
		}
		return result;
	}
}
