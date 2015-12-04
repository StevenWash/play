package com.test.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * 在方法抛出异常时执行的切面逻辑
 * 
 * @author ck
 *
 */
public class TracingThrowsAdvice implements ThrowsAdvice {
	
	public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass) {
		System.out.println(method + "Logging that a " + subclass + "Exception was thrown.");
	}
}
