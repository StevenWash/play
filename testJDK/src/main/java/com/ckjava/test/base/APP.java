package com.ckjava.test.base;

public class APP {
	protected static String a;

	{
		System.out.println("APP {}...");
	}

	public APP() {
		System.out.println("APP Construct...");
	}

	static {
		System.out.println("APP static...");
		a = "s";
	}

}
