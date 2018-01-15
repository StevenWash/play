package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.Method;

public class AInterceptor extends AbstractInterceptorHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("AInterceptor ...");
		if (method.getName().equals("open")) {
			System.out.println("AInterceptor intercept open method");
			return super.invoke(proxy, method, args); // a.调用默认的 open 方法
			//return "替换 open 方法的执行结果"; // b.替换 open 方法的具体实现
		} else {
			return super.invoke(proxy, method, args);	
		}
		
	}
	
}
