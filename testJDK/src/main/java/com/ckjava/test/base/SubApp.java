package com.ckjava.test.base;

public class SubApp extends APP {
	
	{
		System.out.println("SubApp {}...");
	}

	public SubApp() {
		System.out.println("SubApp Construct...");
	}

	static {
		System.out.println("SubApp static...");
		a = "sub s";
	}

	public String getA() {
		System.out.println("invoke SubApp getA");
		return a;
	}

}
