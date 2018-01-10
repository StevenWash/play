package com.ckjava.proxy.run;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.ckjava.proxy.interfaces.IConnection;
import com.ckjava.proxy.interfaces.impl.MyConnectionImpl;
import com.ckjava.proxy.invocationhandlers.AInterceptor;
import com.ckjava.proxy.invocationhandlers.BInterceptor;
import com.ckjava.proxy.invocationhandlers.DefaultInterceptor;

/**
 * 使用动态代理的方式实现责任链设计模式
 * 
 * @author chen_k
 *
 * 2018年1月9日-下午7:39:18
 */
public class AppMain {

	public static void main(String[] args) {

		// 拦截 open 方法
		AInterceptor ainc = new AInterceptor();
		// 拦截 close 方法
		BInterceptor binc = new BInterceptor();
		ainc.setNext(binc);
		// 拦截其他方法
		DefaultInterceptor defc = new DefaultInterceptor(new MyConnectionImpl());
		binc.setNext(defc);
		
		// AInterceptor (next)-> BInterceptor
		try {
			/*Class<?> proxyClass = Proxy.getProxyClass(AppMain.class.getClassLoader(), new Class[] { IConnection.class });
			Constructor<?> proxyClassConstructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
			IConnection conn = (IConnection) proxyClassConstructor.newInstance(ainc); // 这里必须是 AInterceptor 的实例, 由 AInterceptor 一级级向下寻找
			*/
			
			IConnection conn = (IConnection) Proxy.newProxyInstance(AppMain.class.getClassLoader(), new Class[] { IConnection.class }, ainc);
			
			conn.open();
			
			conn.create();
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
