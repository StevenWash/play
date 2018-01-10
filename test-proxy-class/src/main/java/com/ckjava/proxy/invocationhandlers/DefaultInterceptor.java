package com.ckjava.proxy.invocationhandlers;

import java.lang.reflect.Method;

import com.ckjava.proxy.interfaces.IConnection;

public class DefaultInterceptor extends MyInterceptorHandler {

	private IConnection conn;
	
	public DefaultInterceptor(IConnection conn) {
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
		System.out.println("DefaultInterceptor ...");
		return method.invoke(conn, args);
	}
	
}
