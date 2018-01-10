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

}
