package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.Method;

public class AInterceptor extends BInterceptorHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("AInterceptor ...");
		if (method.getName().equals("open")) {
			System.out.println("AInterceptor intercept open method");
			return super.invoke(proxy, method, args);
		} else {
			return super.invoke(proxy, method, args);	
		}
		
	}
	
}
