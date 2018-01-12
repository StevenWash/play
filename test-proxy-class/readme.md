# Java 动态代理的应用

这里基于 Java 动态代理来完成 AOP 编程和注解的实现. 基本步骤如下

- 根据提供的接口数组创建代理类 proxyClass
- 获取代理类 proxyClass 的构造器 proxyClassConstructor, 参数必须实现 InvocationHandler 相关接口
- 根据构造器 proxyClassConstructor 创建 IConnection 类实例, 该实例由jvm创建, 
- 拦截器实现了对代理对象方法在执行过程中的拦截, 从而实现在待拦截方法执行的前后,抛出异常等情况下埋入相关逻辑.
- 拦截器形成拦截器链, 每个拦截器完成不同的拦截任务, 最后一个拦截器必须实现对待拦截对象的调用, 也就是说最后一个拦截器中必须有代理对象的一个实例, 其他前面的拦截器就负责执行前的拦截处理.

相关代码如下

```java
public static void main(String[] args) {

	// 拦截 open 方法
	AInterceptor ainc = new AInterceptor();
	// 拦截 close 方法
	BInterceptor binc = new BInterceptor();
	ainc.setNext(binc);
	// 注解拦截
	AnnotationInterceptor annc = new AnnotationInterceptor();
	binc.setNext(annc);
	// 拦截其他方法
	AopInterceptor defc = new AopInterceptor(new MyConnectionImpl());
	annc.setNext(defc);
	
	// AInterceptor (next)-> BInterceptor
	try {
		/*Class<?> proxyClass = Proxy.getProxyClass(AppMain.class.getClassLoader(), new Class[] { IConnection.class });
		Constructor<?> proxyClassConstructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
		IConnection conn = (IConnection) proxyClassConstructor.newInstance(ainc); // 这里必须是 AInterceptor 的实例, 由 AInterceptor 一级级向下寻找
		*/
		
		IConnection conn = (IConnection) Proxy.newProxyInstance(AppMain.class.getClassLoader(), new Class[] { IConnection.class }, ainc);
		
		conn.open();
		
		conn.create();
		
		conn.get("a");
		
		conn.get("", "");
		
		conn.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
```

## AOP 编程

- 关于 AnnotationInterceptor 的接口的 invoke 方法实现的说明如下

```java
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
```

## 注解的实现

定义注解如下

```java
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
	
}
```

该注解作用于方法的参数上面, 如果参数为 null, 将会抛出 RuntimeException 异常.

将 AnnotationInterceptor 加入责任链后, 即可拦截在应用执行过程中遇到的参数为 null 的问题. AnnotationInterceptor 的实现如下

```java
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
```

