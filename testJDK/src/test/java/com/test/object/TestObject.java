package com.test.object;

public class TestObject {
	public static void main(String[] args) {
		Object obj = new Object();
		obj.equals(new Object());
		System.out.println(obj.hashCode());
		
		System.out.println("line.separator = " + System.getProperty("line.separator", "\n"));
	}
}
