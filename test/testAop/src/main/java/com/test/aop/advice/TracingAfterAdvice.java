package com.test.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * 在方法返回的时候执行的切面逻辑
 * 
 * @author ck
 *
 */
public class TracingAfterAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] arg2, Object arg3) throws Throwable {
		System.out.println(method.getDeclaringClass().getName() + "." + method.getName() + "spend time: " + returnValue);
		System.out.println("execute after (by " + method.getDeclaringClass().getName() + "." + method.getName() + ")");
	}

}
