package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.Method;

public class BInterceptor extends MyInterceptorHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("BInterceptor ...");
		if (method.getName().equals("close")) {
			System.out.println("BInterceptor intercept close method");
			return super.invoke(proxy, method, args);
		} else {
			return super.invoke(proxy, method, args);
		}
	}
	
}
