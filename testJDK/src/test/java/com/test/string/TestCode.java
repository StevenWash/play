package com.test.string;

public class TestCode {

	public static void encode() {
		String name = "Ã‘£°Œ“œ≤ª∂£°";
		toHex(name.toCharArray());
		String[] codeType = { "ISO-8859-1", "GB2312", "GBK", "UTF-16", "UTF-8" };
		for (String type : codeType) {
			try {
				byte[] bytes = name.getBytes(type);
				toHex(type, bytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void toHex(String name, byte[] charArray) {
		System.out.print(String.format("%-15s", name + ":"));
		for (int i = 0; i < charArray.length; i++) {
			System.out.print(String.format("%-8x ", charArray[i]));
		}
		System.out.println();
		System.out.print(String.format("%15s", ""));
		for (int i = 0; i < charArray.length; i++) {
			System.out.print(String.format("%-8s ", ByteToBinary.getBinaryStrFromByte(charArray[i])));
		}
		System.out.println();
	}

	private static void toHex(char[] charArray) {
		System.out.print(String.format("%-15s", "source:"));
		for (int i = 0; i < charArray.length; i++) {
			System.out.print(String.format("%-8x ", (int) charArray[i]));
		}
		System.out.println();
	}

	public static void main(String[] args) {
		encode();
	}

}
