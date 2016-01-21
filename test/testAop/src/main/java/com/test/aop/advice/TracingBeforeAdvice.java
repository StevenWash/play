package com.test.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 方法执行前的切面逻辑
 * 
 * @author ck
 *
 */
public class TracingBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("execute before (by " + method.getDeclaringClass().getName() + "." + method.getName() + ")");
	}

}
