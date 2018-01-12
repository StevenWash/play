package com.ckjava.proxy.interfaces.impl;

import com.ckjava.proxy.interfaces.IConnection;

public class MyConnectionImpl implements IConnection {

	public void open() {
		System.out.println("MyConnectionImpl do open");
	}

	public void close() {
		System.out.println("MyConnectionImpl do close");
	}

	public void create() {
		System.out.println("MyConnectionImpl do create");
	}

	public void get(String key) {
		System.out.println("the key is " + key);
	}

	public void get(String key, String value) {
		System.out.println("the key is " + key + ", the value is " + value);
	//	throw new RuntimeException("ss");
	}

}
