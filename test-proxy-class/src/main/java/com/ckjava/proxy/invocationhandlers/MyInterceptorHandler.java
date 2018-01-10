package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class MyInterceptorHandler implements InvocationHandler {

	private MyInterceptorHandler next;

	public MyInterceptorHandler getNext() {
		return next;
	}

	public void setNext(MyInterceptorHandler next) {
		this.next = next;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (next != null) {
			return next.invoke(proxy, method, args);
		} else {
			throw new NullPointerException();
		}
	}

}
