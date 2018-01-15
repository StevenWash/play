package com.ckjava.proxy.invocationhandlers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.ckjava.proxy.annotations.NotEmpty;
import com.ckjava.proxy.annotations.NotNull;

public class AnnotationInterceptor extends AbstractInterceptorHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("AnnotationInterceptor ...");
		
		// 下面是针对接口中的方法如果带有参数并且加上了 @NotNull 注解的处理
		// 如果参数为 null 就会抛出 RuntimeException 异常
		// method.getParameterAnnotations(); 返回 Annotation 的二维数组, 第一纬表示参数索引, 第二维表示参数对应的注解列表
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] annotations = parameterAnnotations[i];
			for (Annotation annotation : annotations) {
				if (annotation instanceof NotNull && args[i] == null) {
					throw new RuntimeException("AnnotationInterceptor[the parameter has NotNull Annotation, so must be not null, method = "+method.getName()+", arg = "+args[i]+"]");
				}
				if (annotation instanceof NotEmpty && String.valueOf(args[i]).equals("")) {
					throw new RuntimeException("AnnotationInterceptor[the parameter has NotEmpty Annotation, so must be not empty, method = "+method.getName()+", arg = "+args[i]+"]");
				}
			}
		}
		return super.invoke(proxy, method, args);
	}

}
