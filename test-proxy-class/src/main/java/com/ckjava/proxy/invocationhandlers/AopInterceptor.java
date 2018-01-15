package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.Method;

import com.ckjava.proxy.interfaces.IConnection;

public class AopInterceptor extends AbstractInterceptorHandler {

	private IConnection conn;
	
	public AopInterceptor(IConnection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * proxy 由 jvm 创建的类实例, 没有数据状态, 但是可以获取到类的相关信息,比如方法, 注解等信息
	 * method 代理对象的方法
	 * args 代理对象的方法的参数
	 * 
	 * method.invoke(conn, args) 中的 conn 表示在执行过程中具体执行哪个接口实现类, 也可以根据传入方法参数的不同来动态选择实现类, 这里是根据构造函数传入的实现类
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("AopInterceptor ...");
		
		try {
			System.out.println("AopInterceptor 目标方法执行前, before execute");
			Object obj = method.invoke(conn, args);
			System.out.println("AopInterceptor 目标方法执行后, after execute");
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("AopInterceptor 目标方法执行中遇到异常, exception");
			return null;
		} finally {
			System.out.println("AopInterceptor 目标方法在返回前, before returning");
		}
		
	}
	
}
