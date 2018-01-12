package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class BInterceptorHandler implements InvocationHandler {

	private BInterceptorHandler next;

	public BInterceptorHandler getNext() {
		return next;
	}

	public void setNext(BInterceptorHandler next) {
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
