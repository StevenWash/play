package com.test.string;

public class ByteToBinary {
	/**
	 * ��byte����ת����2�����ַ���
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
	 * ��byteת����2�����ַ���
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
			a = (byte) (a >> 1);// ÿ��һλ��ͬ��10����������2��ȥ��������
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
	 * ��byteת����2�����ַ���
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
	 * ��byteת����2�����ַ���
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
